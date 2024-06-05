using AutoMapper;
using MediatR;
using MovieTicket.Core.Domain;
using MovieTicket.Core.Repository;
using MovieTicket.Domain.Entities;
using MovieTicket.Infrastructure.Auth;
using MovieTicketClient.Application.Usecases.Movie;
using MovieTicketClient.Application.Usecases.Reservation.Specs;

namespace MovieTicketClient.Application.Usecases.Reservation;




public class ReservationCreateModel
{
    public Guid ScreeningId { get; set; }
    public ICollection<SeatReservationDto> SeatReservations { get; set; }

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
    private readonly IGridRepository<MovieTicket.Domain.Entities.Reservation> _gridRepository;
    private readonly IMapper _mapper;
    private readonly ISecurityContextAccessor _securityContextAccessor;
    
    public ReservationEndpoint(IRepository<MovieTicket.Domain.Entities.Reservation> repository, IMapper mapper, IRepository<SeatReservation> repositorySeatReservation, ISecurityContextAccessor securityContextAccessor, IGridRepository<MovieTicket.Domain.Entities.Reservation> gridRepository)
    {
        _repository = repository;
        _mapper = mapper;
        _repositorySeatReservation = repositorySeatReservation;
        _securityContextAccessor = securityContextAccessor;
        _gridRepository = gridRepository;
    }

    public async Task<ResultModel<ReservationDto>> Handle(ReservationCreate.Command request, CancellationToken cancellationToken)
    {
        var reservation = new MovieTicket.Domain.Entities.Reservation()
        {
            UserId = Guid.Parse(_securityContextAccessor.GetUserId().ToString() ?? throw new Exception("Unauthorize .")),
            ScreeningId = request.CreateModel.ScreeningId
        };
        await _repository.AddAsync(reservation);
        foreach (var modelSeatReservation in request.CreateModel.SeatReservations)
        {
            var seatReservation = new SeatReservation()
            {
                ReservationId = reservation.Id,
                SeatId = modelSeatReservation.SeatId
            };
            await _repositorySeatReservation.AddAsync(seatReservation);
        }

        return ResultModel<ReservationDto>.Create(_mapper.Map<ReservationDto>(reservation));
    }

    public async Task<ResultModel<ListResultModel<ReservationDto>>> Handle(GetReservations.Query request, CancellationToken cancellationToken)
    {
        var spec = new GetReservationsSpec<ReservationDto>(request);
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