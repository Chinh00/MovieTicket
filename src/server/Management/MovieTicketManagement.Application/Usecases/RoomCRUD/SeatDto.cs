namespace MovieTicketManagement.Application.Usecases.RoomCRUD;

public class SeatDto
{
    public string RowNumber { get; init; }
    public string ColNumber { get; init; }
}

public class SeatCreateModel
{
    public int RowNumber { get; init; }
    public int ColNumber { get; init; }
}