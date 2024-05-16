using AutoMapper;
using MediatR;
using MovieTicket.Core.Domain;
using MovieTicket.Core.Repository;

namespace MovieTicket.Application.Usecases.Screening;


public class ScreeningCreateModel
{
    public Guid MovieId { get; init; }
    public Guid RoomId { get; init; }
    public DateTime StartDate { get; init; }
}

public class ScreeningCreate
{
    public class Command : ICreateCommand<ScreeningCreateModel, ScreeningDto>
    {
        public ScreeningCreateModel CreateModel { get; init; }
    }
}



public class ScreeningEndpoint : IRequestHandler<ScreeningCreate.Command, ResultModel<ScreeningDto>>
{
    private readonly IRepository<Domain.Entities.Screening> _repository;
    private readonly IMapper _mapper;

    public ScreeningEndpoint(IRepository<Domain.Entities.Screening> repository, IMapper mapper)
    {
        _repository = repository;
        _mapper = mapper;
    }

    public async Task<ResultModel<ScreeningDto>> Handle(ScreeningCreate.Command request, CancellationToken cancellationToken)
    {

        var screening = new Domain.Entities.Screening()
        {
            MovieId = request.CreateModel.MovieId,
            RoomId = request.CreateModel.RoomId,
            StartDate = request.CreateModel.StartDate
        };
        await _repository.AddAsync(screening);
        return ResultModel<ScreeningDto>.Create(_mapper.Map<ScreeningDto>(screening));
        
    }
}

public class ScreeningMapperConfig : Profile
{
    public ScreeningMapperConfig()
    {
        CreateMap<Domain.Entities.Screening, ScreeningDto>();
    }
}








