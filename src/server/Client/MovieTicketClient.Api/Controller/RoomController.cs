using Microsoft.AspNetCore.Mvc;
using MovieTicket.Core.Domain;
using MovieTicket.Infrastructure;
using MovieTicket.Infrastructure.Controller;
using MovieTicketClient.Application.Usecases.Room;

namespace MovieTicketClient.Api.Controller;

/// <inheritdoc />
public class RoomController : BaseController
{
    /// <summary>
    /// Get Rooms
    /// </summary>
    /// <param name="query"></param>
    /// <param name="cancellationToken"></param>
    /// <returns></returns>
    [HttpGet]
    public async Task<IActionResult> HandleGetRoomsAsync([FromHeader(Name = "x-query")] string query, CancellationToken cancellationToken = new ())
    {
        var model = HttpContext.SafeGetListQuery<GetRooms.Query, ListResultModel<RoomDto>>(query);
        return Ok(await Mediator.Send(model, cancellationToken));
    }

}