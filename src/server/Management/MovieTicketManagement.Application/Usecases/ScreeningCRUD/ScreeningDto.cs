using MovieTicketManagement.Application.Usecases.MovieCRUD;
using MovieTicketManagement.Application.Usecases.RoomCRUD;

namespace MovieTicketManagement.Application.Usecases.ScreeningCRUD;

public class ScreeningDto
{
    public Guid Id { get; init; }
    public DateTime CreatedDate { get; init; }
    public DateTime? UpdatedDate { get; init; }
    
    public Guid MovieId { get; init; }
    public Guid RoomId { get; init; }
    public DateTime StartDate { get; init; }
    public virtual MovieDto Movie { get; init; }
    public virtual RoomDto Room { get; init; }
}