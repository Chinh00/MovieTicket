using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class Seat : EntityBase
{
    public string RowNumber { get; set; }
    public string ColNumber { get; set; }
    
    public Guid RoomId { get; set; }
    public virtual Room Room { get; set; }
    public virtual ICollection<SeatReservation> SeatReservations { get; set; }
}