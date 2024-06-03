using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class Service : EntityBase
{
    public String Name { get; set; }
    public String Unit { get; set; }
    public Double PriceUnit { get; set; }
    public String Avatar { get; set; }
    
    public virtual ICollection<ServiceReservation> ServiceReservations { get; set; }
}