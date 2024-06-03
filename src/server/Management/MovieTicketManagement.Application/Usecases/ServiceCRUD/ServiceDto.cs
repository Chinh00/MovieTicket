namespace MovieTicketManagement.Application.Usecases.ServiceCRUD;

public class ServiceDto
{
    public Guid Id { get; init; } = Guid.NewGuid();
    public DateTime CreatedDate { get; init; }
    public DateTime? UpdatedDate { get; init; }
    public String Name { get; set; }
    public String Unit { get; set; }
    public Double PriceUnit { get; set; }
    public String Avatar { get; set; }
    
    public virtual ICollection<ServiceReservationDto> ServiceReservations { get; set; }
}