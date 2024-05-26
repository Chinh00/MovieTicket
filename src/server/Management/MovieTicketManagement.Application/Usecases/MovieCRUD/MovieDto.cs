using MovieTicketManagement.Application.Usecases.CategoryCRUD;

namespace MovieTicketManagement.Application.Usecases.MovieCRUD;

public class MovieDto
{
    public Guid Id { get; init; } = Guid.NewGuid();
    public DateTime CreatedDate { get; init; }
    public DateTime? UpdatedDate { get; init; }
    public string Name { get; init; }
    public DateTime ReleaseDate { get; init; }
    public long TotalTime { get; init; }
    public string Description { get; init; }
    
    public string Avatar { get; set; }
    public string Trailer { get; set; }
    
    public virtual ICollection<CategoryDto> Categories { get; init; }
    
    //public virtual ICollection<Screening> Screenings { get; init; }
}