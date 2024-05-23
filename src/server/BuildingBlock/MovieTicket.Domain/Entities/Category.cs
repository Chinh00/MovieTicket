using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class Category : EntityBase
{
    public string Name { get; init; }
    public virtual ICollection<Movie> Movies { get; init; }
}