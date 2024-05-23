using AutoMapper;
using MediatR;
using MovieTicket.Core.Domain;
using MovieTicket.Core.Repository;
using MovieTicket.Domain.Entities;
using MovieTicket.Infrastructure.Auth;

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


public class ReservationEndpoint : IRequestHandler<ReservationCreate.Command, ResultModel<ReservationDto>>
{
    private readonly IRepository<MovieTicket.Domain.Entities.Reservation> _repository;
    private readonly IRepository<SeatReservation> _repositorySeatReservation;
    private readonly IMapper _mapper;
    private readonly ISecurityContextAccessor _securityContextAccessor;
    
    public ReservationEndpoint(IRepository<MovieTicket.Domain.Entities.Reservation> repository, IMapper mapper, IRepository<SeatReservation> repositorySeatReservation, ISecurityContextAccessor securityContextAccessor)
    {
        _repository = repository;
        _mapper = mapper;
        _repositorySeatReservation = repositorySeatReservation;
        _securityContextAccessor = securityContextAccessor;
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
}

public class ReservationMapperConfig : Profile
{
    public ReservationMapperConfig()
    {
        CreateMap<MovieTicket.Domain.Entities.Reservation, ReservationDto>();
        CreateMap<SeatReservation, SeatReservationDto>();
    }
}