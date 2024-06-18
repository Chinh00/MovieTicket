using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class Category : EntityBase
{
    public string Name { get; set; }
    public virtual ICollection<Movie> Movies { get; set; }
}