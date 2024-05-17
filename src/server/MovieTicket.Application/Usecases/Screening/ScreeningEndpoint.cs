using System.Globalization;
using AutoMapper;
using MediatR;
using MovieTicket.Application.Usecases.Screening.Specs;
using MovieTicket.Core.Domain;
using MovieTicket.Core.Repository;

namespace MovieTicket.Application.Usecases.Screening;


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
        public List<string> SortBy { get; init; } = ["Id"];
        public int Page { get; init; } = 1;
        public int PageSize { get; init; } = 20;
    }
}


public class ScreeningEndpoint : IRequestHandler<ScreeningCreate.Command, ResultModel<ScreeningDto>>, IRequestHandler<GetScreenings.Query, ResultModel<ListResultModel<ScreeningDto>>>
{
    private readonly IRepository<Domain.Entities.Screening> _repository;
    private readonly IMapper _mapper;
    private readonly IGridRepository<Domain.Entities.Screening> _gridRepository;

    public ScreeningEndpoint(IRepository<Domain.Entities.Screening> repository, IMapper mapper, IGridRepository<Domain.Entities.Screening> gridRepository)
    {
        _repository = repository;
        _mapper = mapper;
        _gridRepository = gridRepository;
    }

    public async Task<ResultModel<ScreeningDto>> Handle(ScreeningCreate.Command request, CancellationToken cancellationToken)
    {

        var screening = new Domain.Entities.Screening()
        {
            MovieId = request.CreateModel.MovieId,
            RoomId = request.CreateModel.RoomId,
            StartDate = DateTime.ParseExact(request.CreateModel.StartDate, "yyyy-MM-ddTHH:mm:ss.fffZ", CultureInfo.InvariantCulture, DateTimeStyles.AssumeUniversal)
        };
        await _repository.AddAsync(screening);
        return ResultModel<ScreeningDto>.Create(_mapper.Map<ScreeningDto>(screening));
        
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
}

public class ScreeningMapperConfig : Profile
{
    public ScreeningMapperConfig()
    {
        CreateMap<Domain.Entities.Screening, ScreeningDto>();
    }
}








