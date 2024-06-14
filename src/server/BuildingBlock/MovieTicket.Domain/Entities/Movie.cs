using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class Movie : EntityBase
{
    public string Name { get; set; }
    public DateTime ReleaseDate { get; set; }
    public long TotalTime { get; set; }
    public string Description { get; set; }
    
    public string Avatar { get; set; }
    public string Trailer { get; set; }
    
    public virtual ICollection<Category> Categories { get; set; }
    
    public virtual ICollection<Screening> Screenings { get; set; }
    
}