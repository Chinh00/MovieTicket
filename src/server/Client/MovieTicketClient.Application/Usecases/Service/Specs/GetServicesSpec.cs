using MovieTicket.Core.Domain;
using MovieTicket.Core.Specification;

namespace MovieTicketClient.Application.Usecases.Service.Specs;

public sealed class GetServicesSpec<TResponse> : GridSpecificationBase<MovieTicket.Domain.Entities.Service>
{
    public GetServicesSpec(IListQuery<ListResultModel<TResponse>> listQuery)
    {
        ApplyFilterList(listQuery.Filters);
        ApplyIncludeList(listQuery.Includes);
        ApplySortingList(listQuery.SortBy);
        ApplyPaging(listQuery.Page, listQuery.PageSize);
    }
}