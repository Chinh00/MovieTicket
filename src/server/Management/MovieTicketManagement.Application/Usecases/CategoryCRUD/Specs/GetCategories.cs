using MovieTicket.Core.Domain;
using MovieTicket.Core.Specification;
using MovieTicket.Domain.Entities;

namespace MovieTicketManagement.Application.Usecases.CategoryCRUD.Specs;

public sealed class GetCategories<TResponse> : GridSpecificationBase<Category>
{
    public GetCategories(IListQuery<ListResultModel<TResponse>> listQuery)
    {
        ApplyFilterList(listQuery.Filters);
        ApplyIncludeList(listQuery.Includes);
        ApplySortingList(listQuery.SortBy);
        ApplyPaging(listQuery.Page, listQuery.PageSize);
    }
}