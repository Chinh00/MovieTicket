namespace MovieTicketClient.Application.Usecases.Reservation;

public class SeatReservationDto
{
    public Guid SeatId { get; init; }
    public string RowNumber { get; set; }
    public string ColNumber { get; set; }
}