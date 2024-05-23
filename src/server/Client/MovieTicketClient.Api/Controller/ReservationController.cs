using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using MovieTicket.Infrastructure.Controller;
using MovieTicketClient.Application.Usecases.Reservation;

namespace MovieTicketClient.Api.Controller.Client;
[Authorize]
public class ReservationController : BaseController
{
    [HttpPost]
    public async Task<IActionResult> HandleReservationAsync(ReservationCreateModel model, CancellationToken cancellationToken = new())
    {
        return Ok(await Mediator.Send(new ReservationCreate.Command() { CreateModel = model}, cancellationToken));
    }
}