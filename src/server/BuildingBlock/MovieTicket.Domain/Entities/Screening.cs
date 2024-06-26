using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class Screening : EntityBase
{
    public Guid MovieId { get; set; }
    public Guid RoomId { get; set; }
    public DateTime StartDate { get; set; }
    public virtual Movie Movie { get; set; }
    public virtual Room Room { get; set; }
    public virtual ICollection<Reservation> Reservations { get; set; }
    
}