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
    
}