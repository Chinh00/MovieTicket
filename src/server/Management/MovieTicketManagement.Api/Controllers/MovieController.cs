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

    /// <summary>
    /// Update movie by id
    /// </summary>
    /// <param name="id"></param>
    /// <param name="model"></param>
    /// <param name="cancellationToken"></param>
    /// <returns></returns>
    [HttpPut("{id}")]
    public async Task<IActionResult> HandleUpdateMovieAsync(Guid id, MovieUpdateModel model, CancellationToken cancellationToken)
    {
        return Ok(await Mediator.Send(new UpdateMovie.Command() {CreateModel = model}, cancellationToken));
    }

    
    
}