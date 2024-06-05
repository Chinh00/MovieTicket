using MovieTicketClient.Application.Usecases.Service;

namespace MovieTicketClient.Application.Usecases.Reservation;

public class ServiceReservationDto
{
    public Guid ReservationId { get; set; }
    public virtual ReservationDto Reservation { get; set; }
    
    public Guid ServiceId { get; set; }
    public virtual ServiceDto Service { get; set; }
    
    public int Quantity { get; set; }
    public Double Price { get; set; }
}