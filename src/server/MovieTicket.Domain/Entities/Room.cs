using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class Room : EntityBase
{
    public int RoomNumber { get; init; }
    
    public virtual ICollection<Seat> Seats { get; init; }
    public virtual ICollection<Screening> Screenings { get; init; }
}