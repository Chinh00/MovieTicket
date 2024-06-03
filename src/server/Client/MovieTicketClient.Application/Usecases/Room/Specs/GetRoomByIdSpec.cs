using System.Linq.Expressions;
using MovieTicket.Core.Specification;

namespace MovieTicketClient.Application.Usecases.Room.Specs;

public sealed class GetRoomByIdSpec : SpecificationBase<MovieTicket.Domain.Entities.Room>
{
    
    private Guid Id { get; init; }

    public GetRoomByIdSpec(Guid id)
    {
        Id = id;
        AddInclude(e => e.Seats);
    }


    public override Expression<Func<MovieTicket.Domain.Entities.Room, bool>> Criteria
    {
        get
        {
            return e => e.Id == Id;
        }
    }
}