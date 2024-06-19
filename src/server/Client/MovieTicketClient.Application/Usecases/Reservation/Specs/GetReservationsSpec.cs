using MovieTicket.Core.Domain;
using MovieTicket.Core.Specification;

namespace MovieTicketClient.Application.Usecases.Reservation.Specs;

public sealed class GetReservationsSpec<TResponse> : GridSpecificationBase<MovieTicket.Domain.Entities.Reservation> 
{
    public GetReservationsSpec(IListQuery<ListResultModel<TResponse>> listQuery, Guid userId)
    {
        ApplyFilter(e => e.UserId == userId);
        ApplyFilterList(listQuery.Filters);
        ApplyIncludeList(listQuery.Includes);
        ApplySortingList(listQuery.SortBy);
        ApplyPaging(listQuery.Page, listQuery.PageSize);
    }
}