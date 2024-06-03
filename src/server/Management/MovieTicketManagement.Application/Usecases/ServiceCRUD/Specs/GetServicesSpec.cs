using MovieTicket.Core.Domain;
using MovieTicket.Core.Specification;
using MovieTicket.Domain.Entities;

namespace MovieTicketManagement.Application.Usecases.ServiceCRUD.Specs;

public sealed class GetServicesSpec<TResponse> : GridSpecificationBase<Service>
{
    public GetServicesSpec(IListQuery<ListResultModel<TResponse>> listQuery)
    {
        ApplyFilterList(listQuery.Filters);
        ApplyIncludeList(listQuery.Includes);
        ApplySortingList(listQuery.SortBy);
        ApplyPaging(listQuery.Page, listQuery.PageSize);
    }
}