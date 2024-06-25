using MovieTicketClient.Application.Usecases.Screening;

namespace MovieTicketClient.Application.Usecases.Reservation;

public class ReservationDto
{
    public Guid Id { get; set; }
    public Guid ScreeningId { get; set; }
    public Guid UserId { get; set; }
    
    public long TotalPrice { get; set; }
    
    public long ItemPrice { get; set; }
    
    public virtual ScreeningDto Screening { get; set; }
    public virtual ICollection<SeatReservationDto> SeatReservations { get; set; }
    public virtual ICollection<ServiceReservationDto> ServiceReservations { get; set; }
    public DateTime CreatedDate { get; init; }
    public DateTime? UpdatedDate { get; init; }
    
}