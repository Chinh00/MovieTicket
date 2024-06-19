using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using MovieTicket.Core.Domain;
using MovieTicket.Infrastructure;
using MovieTicket.Infrastructure.Controller;
using MovieTicketClient.Application.Usecases.Reservation;

namespace MovieTicketClient.Api.Controller;

/// <inheritdoc />
[Authorize]
public class ReservationController : BaseController
{
    /// <summary>
    /// Create Reservation
    /// </summary>
    /// <param name="model"></param>
    /// <param name="cancellationToken"></param>
    /// <returns></returns>
    [HttpPost]
    public async Task<IActionResult> HandleReservationAsync(ReservationCreateModel model, CancellationToken cancellationToken = new())
    {
        return Ok(await Mediator.Send(new ReservationCreate.Command() { CreateModel = model}, cancellationToken));
    }
    
    /// <summary>
    /// Get Reservation
    /// </summary>
    /// <param name="query"></param>
    /// <param name="cancellationToken"></param>
    /// <returns></returns>
    [HttpGet]
    public async Task<IActionResult> HandleGetReservationsAsync([FromHeader(Name = "x-query")] string query, CancellationToken cancellationToken = new ())
    {
        var model = HttpContext.SafeGetListQuery<GetReservations.Query, ListResultModel<ReservationDto>>(query);
        return Ok(await Mediator.Send(model, cancellationToken ));
    }
}