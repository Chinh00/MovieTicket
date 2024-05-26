namespace MovieTicketManagement.Application.Usecases.RoomCRUD;

public class RoomDto
{
    public Guid Id { get; init; }
    public DateTime CreatedDate { get; init; }
    public DateTime? UpdatedDate { get; init; }
    
    public int RoomNumber { get; init; }
    
    public ICollection<SeatDto> Seats { get; init; }
    
}