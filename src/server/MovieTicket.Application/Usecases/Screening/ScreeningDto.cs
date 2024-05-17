using MovieTicket.Application.Usecases.Movie;
using MovieTicket.Application.Usecases.Room;

namespace MovieTicket.Application.Usecases.Screening;

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