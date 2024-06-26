using MovieTicket.Core.Specification;

namespace MovieTicketClient.Application.Usecases.Room.Specs;

public class GetRoomSeatSpec : GridSpecificationBase<MovieTicket.Domain.Entities.Room>
{
    public GetRoomSeatSpec(Guid roomId, Guid screeningId)
    {
    }
}