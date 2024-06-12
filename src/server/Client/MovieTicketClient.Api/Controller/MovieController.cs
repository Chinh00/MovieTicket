using Microsoft.AspNetCore.Mvc;
using MovieTicket.Core.Domain;
using MovieTicket.Infrastructure;
using MovieTicket.Infrastructure.Controller;
using MovieTicketClient.Application.Usecases.Movie;

namespace MovieTicketClient.Api.Controller;

/// <inheritdoc />
public class MovieController : BaseController
{

    /// <summary>
    /// Get Movies
    /// </summary>
    /// <param name="query"></param>
    /// <param name="cancellationToken"></param>
    /// <returns></returns>
    [HttpGet]
    public async Task<IActionResult> HandleGetMoviesAsync([FromHeader(Name = "x-query")] string query, CancellationToken cancellationToken = new ())
    {
        var model = HttpContext.SafeGetListQuery<GetMovies.Query, ListResultModel<MovieDto>>(query);
        return Ok(await Mediator.Send(model, cancellationToken ));
    }

    /// <summary>
    /// Get Movie by Id
    /// </summary>
    /// <param name="id"></param>
    /// <param name="cancellationToken"></param>
    /// <returns></returns>
    [HttpGet("{id}")]
    public async Task<IActionResult> HandleGetMovieByIdAsync(Guid id, CancellationToken cancellationToken)
    {
        return Ok(await Mediator.Send(new GetMovieById.Query() {Id = id}, cancellationToken));
    }
    
    
}