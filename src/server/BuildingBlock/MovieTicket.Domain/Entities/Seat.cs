using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class Seat : EntityBase
{
    public char RowNumber { get; init; }
    public char ColNumber { get; init; }
    
    public Guid RoomId { get; init; }
    public virtual Room Room { get; init; }
    public virtual ICollection<SeatReservation> SeatReservations { get; init; }
}