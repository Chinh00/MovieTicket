using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class Seat : EntityBase
{
    public int RowNumber { get; init; }
    public int ColNumber { get; init; }
    
    public Guid RoomId { get; init; }
    public virtual Room Room { get; init; }
    
    
    public virtual ICollection<SeatReservation> SeatReservations { get; init; }
}