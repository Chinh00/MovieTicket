using MovieTicket.Core.Domain;
using MovieTicket.Core.Specification;

namespace MovieTicket.Application.Usecases.Screening.Specs;

public sealed class GetScreeningsSpec<TResponse> : GridSpecificationBase<Domain.Entities.Screening>
{
    public GetScreeningsSpec(IListQuery<ListResultModel<TResponse>> listQuery)
    {
        AddInclude(e => e.Movie);
        ApplyFilterList(listQuery.Filters);
        ApplyIncludeList(listQuery.Includes);
        ApplySortingList(listQuery.SortBy);
        ApplyPaging(listQuery.Page, listQuery.PageSize);
    }
}