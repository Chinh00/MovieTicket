namespace MovieTicket.Core.Domain;

public class EntityBase
{
    public Guid Id { get; init; } = Guid.NewGuid();
    public DateTime CreatedDate { get; init; }
    public DateTime? UpdatedDate { get; init; }
    
}