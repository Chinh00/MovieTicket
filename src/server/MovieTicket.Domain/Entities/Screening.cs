using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class Screening : EntityBase
{
    public Guid MovieId { get; init; }
    public Guid RoomId { get; init; }
    public DateTime StartDate { get; init; }
    
    public virtual Movie Movie { get; init; }
    public virtual Room Room { get; init; }
    public virtual ICollection<Reservation> Reservations { get; init; }
    
}