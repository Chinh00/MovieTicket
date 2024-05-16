using MovieTicket.Core.Domain;
using MovieTicket.Core.Specification;

namespace MovieTicket.Application.Usecases.Room.Specs;

public sealed class GetRoomsSpec<TResponse> : GridSpecificationBase<Domain.Entities.Room>
{
    public GetRoomsSpec(IListQuery<ListResultModel<TResponse>> listQuery)
    {
        AddInclude(e => e.Seats);
        ApplyFilterList(listQuery.Filters);
        ApplyIncludeList(listQuery.Includes);
        ApplySortingList(listQuery.SortBy);
        ApplyPaging(listQuery.Page, listQuery.PageSize);
    }
}