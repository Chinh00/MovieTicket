using Microsoft.AspNetCore.Mvc;

namespace MovieTicketClient.Api.Controller.Admin;

public class RoomController : AdminControllerBase
{
    [HttpGet]
    public async Task<IActionResult> HandleGetRoomsAsync([FromHeader(Name = "x-query")] string query, CancellationToken cancellationToken = new ())
    {
        var model = HttpContext.SafeGetListQuery<GetRooms.Query, ListResultModel<RoomDto>>(query);
        return Ok(await Mediator.Send(model, cancellationToken));
    }
}