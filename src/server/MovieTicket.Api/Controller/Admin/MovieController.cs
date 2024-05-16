using Microsoft.AspNetCore.Mvc;
using MovieTicket.Application.Usecases.Movie;
using MovieTicket.Core.Domain;
using MovieTicket.Infrastructure;

namespace MovieTicket.Api.Controller.Admin;

public class MovieController : AdminControllerBase
{
    [HttpGet]
    public async Task<IActionResult> HandleGetMoviesAsync([FromHeader(Name = "x-query")] string query, CancellationToken cancellationToken = new ())
    {

        var model = HttpContext.SafeGetListQuery<GetMovies.Query, ListResultModel<MovieDto>>(query);
        return Ok(await Mediator.Send(model, cancellationToken));
    }
    
    
    [HttpPost]
    public async Task<IActionResult> HandleMovieCreateAsync(MovieCreateModel createModel, CancellationToken cancellationToken = new ())
    {
        return Ok(await Mediator.Send(new MovieCreate.Command() { CreateModel = createModel }, cancellationToken));
    }

    [HttpPut]
    public async Task<IActionResult> HandleMovieUpdateAsync(MovieCreateModel createModel,
        CancellationToken cancellationToken = new())
    {
        return Ok(await Mediator.Send(new MovieUpdate.Command() { CreateModel = createModel }, cancellationToken));
    }
    
    
    
}