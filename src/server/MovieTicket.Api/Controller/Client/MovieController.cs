using Microsoft.AspNetCore.Mvc;
using MovieTicket.Application.Usecases.Movie;
using MovieTicket.Core.Domain;
using MovieTicket.Infrastructure;
using MovieTicket.Infrastructure.Auth;

namespace MovieTicket.Api.Controller.Client;

public class MovieController : ClientControllerBase
{

    [HttpGet]
    public async Task<IActionResult> HandleGetMoviesAsync([FromHeader(Name = "x-query")] string query, CancellationToken cancellationToken = new ())
    {
        var model = HttpContext.SafeGetListQuery<GetMovies.Query, ListResultModel<MovieDto>>(query);
        return Ok(await Mediator.Send(model, cancellationToken ));
    }
    
}