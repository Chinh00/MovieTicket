using MovieTicket.Core.Specification;

namespace MovieTicketClient.Application.Usecases.Service.Specs;

public sealed class GetServiceByListIdSpecs : GridSpecificationBase<MovieTicket.Domain.Entities.Service>
{
    public GetServiceByListIdSpecs(ICollection<Guid> guids)
    {
        foreach (var guid in guids)
        {
            ApplyFilter(e => e.Id == guid);
        }
    }
}