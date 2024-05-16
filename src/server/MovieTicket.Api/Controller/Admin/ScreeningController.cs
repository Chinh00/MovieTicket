using Microsoft.AspNetCore.Mvc;
using MovieTicket.Application.Usecases.Screening;

namespace MovieTicket.Api.Controller.Admin;

public class ScreeningController : AdminControllerBase
{
    [HttpPost]
    public async Task<IActionResult> HandleCreateScreeningAsync(ScreeningCreateModel model, CancellationToken cancellationToken = new ())
    {
        return Ok(await Mediator.Send(new ScreeningCreate.Command() { CreateModel = model }, cancellationToken));
    }
}