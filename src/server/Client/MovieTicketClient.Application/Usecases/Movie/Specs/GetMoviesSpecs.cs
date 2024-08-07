using MovieTicket.Core.Domain;
using MovieTicket.Core.Specification;

namespace MovieTicketClient.Application.Usecases.Movie.Specs;

public sealed class GetMoviesSpecs<TResponse> : GridSpecificationBase<MovieTicket.Domain.Entities.Movie>
{
    public GetMoviesSpecs(IListQuery<ListResultModel<TResponse>> listQuery)
    {
        ApplyFilterList(listQuery.Filters);
        ApplyIncludeList(listQuery.Includes);
        ApplySortingList(listQuery.SortBy);
        ApplyPaging(listQuery.Page, listQuery.PageSize);
    }
}