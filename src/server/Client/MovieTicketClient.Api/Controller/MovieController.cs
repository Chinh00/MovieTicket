using Microsoft.AspNetCore.Mvc;
using MovieTicket.Core.Domain;
using MovieTicket.Infrastructure;
using MovieTicket.Infrastructure.Controller;
using MovieTicketClient.Application.Usecases.Movie;

namespace MovieTicketClient.Api.Controller;

public class MovieController : BaseController
{

    [HttpGet]
    public async Task<IActionResult> HandleGetMoviesAsync([FromHeader(Name = "x-query")] string query, CancellationToken cancellationToken = new ())
    {
        var model = HttpContext.SafeGetListQuery<GetMovies.Query, ListResultModel<MovieDto>>(query);
        return Ok(await Mediator.Send(model, cancellationToken ));
    }
    
}