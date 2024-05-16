using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class Reservation : EntityBase
{
    public Guid ScreeningId { get; init; }
    public Guid UserId { get; init; }
    
    
    public virtual Screening Screening { get; init; }
    public virtual User User { get; init; }
    public virtual ICollection<SeatReservation> SeatReservations { get; init; }
    
    
}