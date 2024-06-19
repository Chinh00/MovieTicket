using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class Reservation : EntityBase
{
    public Guid ScreeningId { get; set; }
    public Guid UserId { get; set; }
    
    public long TotalPrice { get; set; }
    
    public long ItemPrice { get; set; }

    public string TransactionId { get; set; }
    
    public ReservationState ReservationState { get; set; }
    
    public virtual Screening Screening { get; set; }
    public virtual ICollection<SeatReservation> SeatReservations { get; set; }
    public virtual ICollection<ServiceReservation> ServiceReservations { get; set; }
    

}

