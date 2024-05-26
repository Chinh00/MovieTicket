using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using MovieTicket.Core.Domain;
using MovieTicket.Infrastructure;
using MovieTicket.Infrastructure.Controller;
using MovieTicketManagement.Application.Usecases.MovieCRUD;

namespace MovieTicketManagement.Api.Controllers;

/// <inheritdoc />
[Authorize]
public class MovieController : BaseController
{
    /// <summary>
    /// Create movie
    /// </summary>
    /// <param name="model"></param>
    /// <param name="cancellationToken"></param>
    /// <returns></returns>
    [HttpPost]
    public async Task<IActionResult> HandleCreateMovieAsync([FromForm] MovieCreateModel model, CancellationToken cancellationToken = new ())
    {
        return Ok(await Mediator.Send(new MovieCreate.Command()
        {
            CreateModel = model
        }, cancellationToken));
    }
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