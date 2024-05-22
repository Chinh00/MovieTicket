using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using MovieTicket.Application.Usecases.Reservation;
using MovieTicket.Infrastructure.Auth;

namespace MovieTicket.Api.Controller.Client;
[Authorize]
public class ReservationController : ClientControllerBase
{
    [HttpPost]
    public async Task<IActionResult> HandleReservationAsync(ReservationCreateModel model, CancellationToken cancellationToken = new())
    {
        return Ok(await Mediator.Send(new ReservationCreate.Command() { CreateModel = model}, cancellationToken));
    }
}