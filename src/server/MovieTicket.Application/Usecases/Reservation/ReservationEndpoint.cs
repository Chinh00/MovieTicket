using AutoMapper;
using MediatR;
using MovieTicket.Core.Domain;
using MovieTicket.Core.Repository;
using MovieTicket.Domain.Entities;

namespace MovieTicket.Application.Usecases.Reservation;




public class ReservationCreateModel
{
    public Guid ScreeningId { get; init; }
    public Guid UserId { get; init; }
    public ICollection<SeatReservationDto> SeatReservations { get; init; }

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
    private readonly IRepository<Domain.Entities.Reservation> _repository;
    private readonly IRepository<SeatReservation> _repositorySeatReservation;
    private readonly IMapper _mapper;
    
    public ReservationEndpoint(IRepository<Domain.Entities.Reservation> repository, IMapper mapper, IRepository<SeatReservation> repositorySeatReservation)
    {
        _repository = repository;
        _mapper = mapper;
        _repositorySeatReservation = repositorySeatReservation;
    }

    public async Task<ResultModel<ReservationDto>> Handle(ReservationCreate.Command request, CancellationToken cancellationToken)
    {
        var reservation = new Domain.Entities.Reservation()
        {

        };
        await _repository.AddAsync(reservation);
        foreach (var modelSeatReservation in request.CreateModel.SeatReservations)
        {
            var seat_reservation = new SeatReservation()
            {
                ReservationId = reservation.Id,
                SeatId = modelSeatReservation.SeatId
            };
            
        }
        
    }
}

public class ReservationMapperConfig : Profile
{
    public ReservationMapperConfig()
    {
        CreateMap<Domain.Entities.Reservation, ReservationDto>();
        CreateMap<SeatReservation, SeatReservationDto>();
    }
}