using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class Room : EntityBase
{
    public int Number { get; init; }
    public int RowNumber { get; init; }
    public int ColNumber { get; init; }
}