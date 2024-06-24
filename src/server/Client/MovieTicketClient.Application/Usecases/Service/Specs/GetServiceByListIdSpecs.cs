using MovieTicket.Core.Specification;

namespace MovieTicketClient.Application.Usecases.Service.Specs;

public sealed class GetServiceByListIdSpecs : GridSpecificationBase<MovieTicket.Domain.Entities.Service>
{
    public GetServiceByListIdSpecs(ICollection<Guid> guids)
    {
        if (guids.Count == 0) return;
        ApplyFilter(e => guids.Contains(e.Id));
    }
}