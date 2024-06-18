using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class SeatReservation : EntityBase
{
    public Guid? SeatId { get; set; }
    public Guid? ReservationId { get; set; }
    
    public virtual Reservation Reservation { get; set; }
    
    public virtual Seat Seat { get; set; }
}