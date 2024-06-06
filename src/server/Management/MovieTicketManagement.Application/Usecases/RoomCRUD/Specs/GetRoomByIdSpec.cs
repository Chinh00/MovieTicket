using System.Linq.Expressions;
using MovieTicket.Core.Specification;
using MovieTicket.Domain.Entities;

namespace MovieTicketManagement.Application.Usecases.RoomCRUD.Specs;

public sealed class GetRoomByIdSpec : SpecificationBase<Room>
{
    private Guid Id { get; init; }

    public GetRoomByIdSpec(Guid id)
    {;
        Id = id;
        AddInclude(e => e.Seats);
    }

    public override Expression<Func<Room, bool>> Criteria
    {
        get
        {
            return e => e.Id == Id;
        }
    }
}