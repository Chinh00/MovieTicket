using MovieTicket.Core.Domain;
using MovieTicket.Core.Specification;
using MovieTicket.Domain.Entities;

namespace MovieTicketManagement.Application.Usecases.RoomCRUD.Specs;

public sealed class GetRoomsSpec<TResponse> : GridSpecificationBase<Room>
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