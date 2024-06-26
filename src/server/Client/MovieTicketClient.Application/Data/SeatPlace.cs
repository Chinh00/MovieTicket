namespace MovieTicketClient.Application.Data;

public class SeatPlace
{
    public Guid Id { get; set; }
    public Guid RoomId { get; set; }
    public Guid SeatId { get; set; }
    public Guid ScreeningId { get; set; }
    public bool IsPlace { get; set; }
}