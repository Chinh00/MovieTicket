using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using MovieTicket.Core.Domain;
using MovieTicket.Infrastructure;
using MovieTicket.Infrastructure.Controller;
using MovieTicketManagement.Application.Usecases.RoomCRUD;

namespace MovieTicketManagement.Api.Controllers;

/// <inheritdoc />
[Authorize]
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

    /// <summary>
    /// Create room
    /// </summary>
    /// <param name="model"></param>
    /// <param name="cancellationToken"></param>
    /// <returns></returns>
    [HttpPost]
    public async Task<IActionResult> HandleCreateRoomAsync(RoomCreateModel model, CancellationToken cancellationToken = new ())
    {
        return Ok(await Mediator.Send(new CreateRoom.Command()
        {
            CreateModel = model
        }, cancellationToken));
    }
}