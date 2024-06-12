using System.Linq.Expressions;
using MovieTicket.Core.Specification;

namespace MovieTicketClient.Application.Usecases.Movie.Specs;

public sealed class GetMovieByIdSpec : SpecificationBase<MovieTicket.Domain.Entities.Movie>
{
    private Guid Id { get; init; }

    public GetMovieByIdSpec(Guid id)
    {
        Id = id;
        AddInclude(e => e.Categories);
    }

    public override Expression<Func<MovieTicket.Domain.Entities.Movie, bool>> Criteria
    {
        get
        {
            return e => e.Id == Id;
        }
    }
}