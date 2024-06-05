using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class Seat : EntityBase
{
    public string RowNumber { get; init; }
    public string ColNumber { get; init; }
    
    public Guid RoomId { get; init; }
    public virtual Room Room { get; init; }
    public virtual ICollection<SeatReservation> SeatReservations { get; init; }
}