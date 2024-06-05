namespace MovieTicketClient.Application.Usecases.Movie;

public class MovieDto
{
    public Guid Id { get; init; }
    public string Name { get; init; }
    public DateTime ReleaseDate { get; init; }
    public long TotalTime { get; init; }
    public string Description { get; init; }
    public string Avatar { get; init; }
    public string Trailer { get; init; }
    public virtual ICollection<CategoryDto> Categories { get; init; }

}