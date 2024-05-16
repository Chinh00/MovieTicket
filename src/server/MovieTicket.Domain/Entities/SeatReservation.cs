using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class SeatReservation : EntityBase
{
    public Guid? SeatId { get; init; }
    public Guid? ReservationId { get; init; }
    
    public virtual Reservation Reservation { get; init; }
    
    public virtual Seat Seat { get; init; }
}