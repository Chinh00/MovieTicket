using Microsoft.AspNetCore.Mvc;
using MovieTicket.Application.Usecases.Room;
using MovieTicket.Core.Domain;
using MovieTicket.Infrastructure;

namespace MovieTicket.Api.Controller.Admin;

public class RoomController : AdminControllerBase
{
    [HttpGet]
    public async Task<IActionResult> HandleGetRoomsAsync([FromHeader(Name = "x-query")] string query, CancellationToken cancellationToken = new ())
    {
        var model = HttpContext.SafeGetListQuery<GetRooms.Query, ListResultModel<RoomDto>>(query);
        return Ok(await Mediator.Send(model, cancellationToken));
    }
}