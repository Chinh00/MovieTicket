using MovieTicketClient.Application.Usecases.Screening;

namespace MovieTicketClient.Application.Usecases.Reservation;

public class ReservationDto
{
    public Guid ScreeningId { get; set; }
    public Guid UserId { get; set; }
    
    public Guid TotalPrice { get; set; }
    
    public Guid ItemPrice { get; set; }
    
    public virtual ScreeningDto Screening { get; set; }
    public virtual ICollection<SeatReservationDto> SeatReservations { get; set; }
    public virtual ICollection<ServiceReservationDto> ServiceReservations { get; set; }
}