using Microsoft.AspNetCore.Mvc;
using MovieTicket.Core.Domain;
using MovieTicket.Infrastructure;
using MovieTicket.Infrastructure.Controller;
using MovieTicketClient.Application.Usecases.Screening;

namespace MovieTicketClient.Api.Controller;

public class ScreeningController : BaseController
{
    [HttpGet]
    public async Task<IActionResult> HandleGetScreeningsAsync([FromHeader(Name = "x-query")] string query, CancellationToken cancellationToken = new ())
    {
        var model = HttpContext.SafeGetListQuery<GetScreenings.Query, ListResultModel<ScreeningDto>>(query);
        return Ok(await Mediator.Send(model, cancellationToken));
    }
}