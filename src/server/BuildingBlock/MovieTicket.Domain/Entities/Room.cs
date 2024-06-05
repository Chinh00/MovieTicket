using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class Room : EntityBase
{
    public int RoomNumber { get; set; }
    
    public virtual ICollection<Seat> Seats { get; set; }
    public virtual ICollection<Screening> Screenings { get; set; }
}