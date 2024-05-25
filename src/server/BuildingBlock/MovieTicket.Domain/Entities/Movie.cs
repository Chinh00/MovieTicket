using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class Movie : EntityBase
{
    public string Name { get; init; }
    public DateTime ReleaseDate { get; init; }
    public long TotalTime { get; init; }
    public string Description { get; init; }
    
    public string Avatar { get; set; }
    public string Trailer { get; set; }
    
    public virtual ICollection<Category> Categories { get; init; }
    
    public virtual ICollection<Screening> Screenings { get; init; }
    
}