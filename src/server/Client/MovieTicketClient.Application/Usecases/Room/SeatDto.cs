namespace MovieTicketClient.Application.Usecases.Room;

public class SeatDto
{
    public Guid Id { get; init; }
    public int RowNumber { get; init; }
    public int ColNumber { get; init; }
}