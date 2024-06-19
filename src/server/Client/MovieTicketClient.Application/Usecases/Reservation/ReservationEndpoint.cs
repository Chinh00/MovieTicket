using AutoMapper;
using MediatR;
using MovieTicket.Core.Domain;
using MovieTicket.Core.Repository;
using MovieTicket.Domain.Entities;
using MovieTicket.Infrastructure.Auth;
using MovieTicketClient.Application.Usecases.Movie;
using MovieTicketClient.Application.Usecases.Reservation.Specs;
using MovieTicketClient.Application.Usecases.Room.Specs;
using MovieTicketClient.Application.Usecases.Service;
using MovieTicketClient.Application.Usecases.Service.Specs;

namespace MovieTicketClient.Application.Usecases.Reservation;




public class ReservationCreateModel
{
    public Guid ScreeningId { get; set; }
    public ICollection<SeatReservationDto> SeatReservations { get; set; }
    public ICollection<ServiceReservationModel> ServiceReservations { get; set; }
    public string TransactionId { get; set; }
    public ReservationState ReservationState { get; set; }

}

public class ServiceReservationModel
{
    
    public Guid ServiceId { get; set; }
    public int Quantity { get; set; }
    
}


public class ReservationCreate
{
    public record Command: ICreateCommand<ReservationCreateModel, ReservationDto>
    {
        public ReservationCreateModel CreateModel { get; init; }
    }
}

public class GetReservations
{
    public record Query : IListQuery<ListResultModel<ReservationDto>>
    {
        public List<FilterModel> Filters { get; init; } = [];
        public List<string> Includes { get; init; } = [];
        public List<string> SortBy { get; init; } = [];
        public int Page { get; init; } = 1;
        public int PageSize { get; init; } = 20;
    }
}



public class ReservationEndpoint : IRequestHandler<ReservationCreate.Command, ResultModel<ReservationDto>>, IRequestHandler<GetReservations.Query, ResultModel<ListResultModel<ReservationDto>>>
{
    private readonly IRepository<MovieTicket.Domain.Entities.Reservation> _repository;
    private readonly IRepository<SeatReservation> _repositorySeatReservation;
    private readonly IRepository<MovieTicket.Domain.Entities.Service> _repositoryService;
    private readonly IGridRepository<MovieTicket.Domain.Entities.Service> _gridRepositoryService;
    private readonly IGridRepository<MovieTicket.Domain.Entities.Reservation> _gridRepository;
    private readonly IGridRepository<Seat> _gridRepositorySeat;
    private readonly IMapper _mapper;
    private readonly ISecurityContextAccessor _securityContextAccessor;
    
    public ReservationEndpoint(IRepository<MovieTicket.Domain.Entities.Reservation> repository, IMapper mapper, IRepository<SeatReservation> repositorySeatReservation, ISecurityContextAccessor securityContextAccessor, IGridRepository<MovieTicket.Domain.Entities.Reservation> gridRepository, IRepository<MovieTicket.Domain.Entities.Service> repositoryService, IGridRepository<MovieTicket.Domain.Entities.Service> gridRepositoryService, IGridRepository<Seat> gridRepositorySeat)
    {
        _repository = repository;
        _mapper = mapper;
        _repositorySeatReservation = repositorySeatReservation;
        _securityContextAccessor = securityContextAccessor;
        _gridRepository = gridRepository;
        _repositoryService = repositoryService;
        _gridRepositoryService = gridRepositoryService;
        _gridRepositorySeat = gridRepositorySeat;
    }

    public async Task<ResultModel<ReservationDto>> Handle(ReservationCreate.Command request, CancellationToken cancellationToken)
    {
        var reservation = new MovieTicket.Domain.Entities.Reservation()
        {
            UserId = Guid.Parse(_securityContextAccessor.GetUserId().ToString() ?? throw new Exception("Unauthorize .")),
            ScreeningId = request.CreateModel.ScreeningId
        };
        await _repository.AddAsync(reservation);

        var seatsByIdSpec = new GetSeatsByIdSpec(request.CreateModel.SeatReservations.Select(e => e.SeatId).ToList());
        var seatsInclude = await _gridRepositorySeat.FindAsync(seatsByIdSpec);
        foreach (var seat in seatsInclude)
        {
            await _repositorySeatReservation.AddAsync(new SeatReservation()
            {
                SeatId = seat.Id,
                ReservationId = reservation.Id
            });
        }
        

        return ResultModel<ReservationDto>.Create(_mapper.Map<ReservationDto>(reservation));
    }

    
    

    public async Task<ResultModel<ListResultModel<ReservationDto>>> Handle(GetReservations.Query request, CancellationToken cancellationToken)
    {
        var spec = new GetReservationsSpec<ReservationDto>(request, Guid.Parse(_securityContextAccessor.GetUserId().ToString() ?? string.Empty));
        var reservations = await _gridRepository.FindAsync(spec);
        var reservationTotals = await _gridRepository.CountAsync(spec);
        var listResultModel = ListResultModel<ReservationDto>.Create(_mapper.Map<List<ReservationDto>>(reservations), reservationTotals,
            request.Page, request.PageSize);
        
        return ResultModel<ListResultModel<ReservationDto>>.Create(listResultModel);
    }
}

public class ReservationMapperConfig : Profile
{
    public ReservationMapperConfig()
    {
        CreateMap<MovieTicket.Domain.Entities.Reservation, ReservationDto>();
        CreateMap<SeatReservation, SeatReservationDto>();
        CreateMap<ServiceReservation, ServiceReservationDto>();
    }
}