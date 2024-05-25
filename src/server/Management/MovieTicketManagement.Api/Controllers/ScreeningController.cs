using Microsoft.AspNetCore.Mvc;
using MovieTicket.Core.Domain;
using MovieTicket.Infrastructure;
using MovieTicket.Infrastructure.Controller;
using MovieTicketManagement.Application.Usecases.ScreeningCRUD;

namespace MovieTicketManagement.Api.Controllers;

/// <inheritdoc />
public class ScreeningController : BaseController
{
    /// <summary>
    /// Get screenings
    /// </summary>
    /// <param name="query"></param>
    /// <param name="cancellationToken"></param>
    /// <returns></returns>
    [HttpGet]
    public async Task<IActionResult> HandleGetScreeningsAsync([FromHeader(Name = "x-query")] string query, CancellationToken cancellationToken = new ())
    {
        var model = HttpContext.SafeGetListQuery<GetScreenings.Query, ListResultModel<ScreeningDto>>(query);
        return Ok(await Mediator.Send(model, cancellationToken));
    }
    
    /// <summary>
    /// Create screening
    /// </summary>
    /// <param name="model"></param>
    /// <param name="cancellationToken"></param>
    /// <returns></returns>
    [HttpPost]
    public async Task<IActionResult> HandleCreateScreeningAsync(ScreeningCreateModel model, CancellationToken cancellationToken = new ())
    {
        return Ok(await Mediator.Send(new ScreeningCreate.Command() {CreateModel = model}, cancellationToken));
    }
}