using MovieTicket.Core.Specification;
using MovieTicket.Domain.Entities;

namespace MovieTicketClient.Application.Usecases.Room.Specs;

public sealed class GetSeatsByIdSpec : GridSpecificationBase<Seat>
{
    public GetSeatsByIdSpec(List<Guid> guids)
    {
        if (guids.Count <= 0) return;
        ApplyFilter(e => guids.Contains(e.Id));
    }
}