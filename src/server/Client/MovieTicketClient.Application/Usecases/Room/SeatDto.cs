namespace MovieTicketClient.Application.Usecases.Room;

public class SeatDto
{
    public Guid Id { get; init; }
    public string RowNumber { get; init; }
    public string ColNumber { get; init; }
}