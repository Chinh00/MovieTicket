using MovieTicketManagement.Application.Usecases.ReservationCRUD;

namespace MovieTicketManagement.Application.Usecases.ServiceCRUD;

public class ServiceReservationDto
{
    public Guid Id { get; init; } = Guid.NewGuid();
    public DateTime CreatedDate { get; init; }
    public DateTime? UpdatedDate { get; init; }
    public Guid ReservationId { get; set; }
    public virtual ReservationDto Reservation { get; set; }
    
    public Guid ServiceId { get; set; }
    public virtual ServiceDto Service { get; set; }
    
    public int Quantity { get; set; }
    public Double Price { get; set; }
}