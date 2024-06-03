using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class Reservation : EntityBase
{
    public Guid ScreeningId { get; set; }
    public Guid UserId { get; set; }
    
    public Guid TotalPrice { get; set; }
    
    public Guid ItemPrice { get; set; }
    
    public virtual Screening Screening { get; set; }
    public virtual ICollection<SeatReservation> SeatReservations { get; set; }
    public virtual ICollection<ServiceReservation> ServiceReservations { get; set; }
    
    
}