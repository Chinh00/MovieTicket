using System.Globalization;
using AutoMapper;
using MediatR;
using MovieTicket.Core.Domain;
using MovieTicket.Core.Repository;
using MovieTicket.Domain.Entities;
using MovieTicketManagement.Application.Usecases.ScreeningCRUD.Specs;

namespace MovieTicketManagement.Application.Usecases.ScreeningCRUD;

public class ScreeningCreateModel
{
    public Guid MovieId { get; init; }
    public Guid RoomId { get; init; }
    public string StartDate { get; init; }
}
public class ScreeningCreate
{
    public class Command : ICreateCommand<ScreeningCreateModel, ScreeningDto>
    {
        public ScreeningCreateModel CreateModel { get; init; }
    }
}

public class GetScreenings
{
    public class Query : IListQuery<ListResultModel<ScreeningDto>>
    {
        public List<FilterModel> Filters { get; init; } = [];
        public List<string> Includes { get; init; } = [];
        public List<string> SortBy { get; init; } = [];
        public int Page { get; init; } = 1;
        public int PageSize { get; init; } = 20;
    }
}

public class ScreeningCrud : IRequestHandler<GetScreenings.Query, ResultModel<ListResultModel<ScreeningDto>>>,  IRequestHandler<ScreeningCreate.Command, ResultModel<ScreeningDto>>
{
    private readonly IRepository<MovieTicket.Domain.Entities.Screening> _repository;
    private readonly IMapper _mapper;
    private readonly IGridRepository<MovieTicket.Domain.Entities.Screening> _gridRepository;

    public ScreeningCrud(IMapper mapper, IRepository<Screening> repository, IGridRepository<Screening> gridRepository)
    {
        _mapper = mapper;
        _repository = repository;
        _gridRepository = gridRepository;
    }

    public async Task<ResultModel<ListResultModel<ScreeningDto>>> Handle(GetScreenings.Query request, CancellationToken cancellationToken)
    {
        var spec = new GetScreeningsSpec<ScreeningDto>(request);
        var screenings = await _gridRepository.FindAsync(spec);
        var totalScreenings = await _gridRepository.CountAsync(spec);
        var listResultModel = ListResultModel<ScreeningDto>.Create(_mapper.Map<List<ScreeningDto>>(screenings),
            totalScreenings, request.Page, request.PageSize);
        return ResultModel<ListResultModel<ScreeningDto>>.Create(listResultModel);
    }

    public async Task<ResultModel<ScreeningDto>> Handle(ScreeningCreate.Command request, CancellationToken cancellationToken)
    {
        var screening = new MovieTicket.Domain.Entities.Screening()
        {
            MovieId = request.CreateModel.MovieId,
            RoomId = request.CreateModel.RoomId,
            StartDate = DateTime.ParseExact(request.CreateModel.StartDate, "yyyy-MM-ddTHH:mm:ss.fffZ", null, DateTimeStyles.None)
        };
        await _repository.AddAsync(screening);
        return ResultModel<ScreeningDto>.Create(_mapper.Map<ScreeningDto>(screening));
    }
}

public class ScreeningMapperConfig : Profile
{
    public ScreeningMapperConfig()
    {
        CreateMap<MovieTicket.Domain.Entities.Screening, ScreeningDto>();
    }
}
