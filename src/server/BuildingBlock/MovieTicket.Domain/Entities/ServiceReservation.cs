using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class ServiceReservation :  EntityBase
{
    public Guid ReservationId { get; set; }
    public virtual Reservation Reservation { get; set; }
    
    public Guid ServiceId { get; set; }
    public virtual Service Service { get; set; }
    
    public int Quantity { get; set; }
    public Double Price { get; set; }
}