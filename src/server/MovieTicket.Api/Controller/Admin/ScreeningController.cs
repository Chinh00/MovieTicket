using Microsoft.AspNetCore.Mvc;
using MovieTicket.Application.Usecases.Screening;
using MovieTicket.Core.Domain;
using MovieTicket.Infrastructure;

namespace MovieTicket.Api.Controller.Admin;

public class ScreeningController : AdminControllerBase
{
    [HttpPost]
    public async Task<IActionResult> HandleCreateScreeningAsync(ScreeningCreateModel model, CancellationToken cancellationToken = new ())
    {
        return Ok(await Mediator.Send(new ScreeningCreate.Command() { CreateModel = model }, cancellationToken));
    }
    [HttpGet]
    public async Task<IActionResult> HandleGetScreeningsAsync([FromHeader(Name = "x-query")] string query, CancellationToken cancellationToken = new ())
    {
        var model = HttpContext.SafeGetListQuery<GetScreenings.Query, ListResultModel<ScreeningDto>>(query);
        return Ok(await Mediator.Send(model, cancellationToken));
    }
}