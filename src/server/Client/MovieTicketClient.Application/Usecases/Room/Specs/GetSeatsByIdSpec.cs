using MovieTicket.Core.Specification;
using MovieTicket.Domain.Entities;

namespace MovieTicketClient.Application.Usecases.Room.Specs;

public sealed class GetSeatsByIdSpec : GridSpecificationBase<Seat>
{
    public GetSeatsByIdSpec(List<Guid> guids)
    {
        if (guids.Count <= 0) return;
        foreach (var guid in guids)
        {
            ApplyFilter(e => e.Id == guid);
        }
    }
}