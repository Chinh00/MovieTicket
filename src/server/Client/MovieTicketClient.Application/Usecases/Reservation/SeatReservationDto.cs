using MovieTicketClient.Application.Usecases.Room;

namespace MovieTicketClient.Application.Usecases.Reservation;

public class SeatReservationDto
{
    public Guid SeatId { get; init; }
    public virtual SeatDto Seat { get; set; }
    
    public Guid? ReservationId { get; set; }
    
    public virtual ReservationDto Reservation { get; set; }
    
}