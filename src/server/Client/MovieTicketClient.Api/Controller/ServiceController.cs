using Microsoft.AspNetCore.Mvc;
using MovieTicket.Core.Domain;
using MovieTicket.Infrastructure;
using MovieTicket.Infrastructure.Controller;
using MovieTicketClient.Application.Usecases.Service;

namespace MovieTicketClient.Api.Controller;

/// <inheritdoc />
public class ServiceController : BaseController
{
    /// <summary>
    /// Get Screenings
    /// </summary>
    /// <param name="query"></param>
    /// <param name="cancellationToken"></param>
    /// <returns></returns>
    [HttpGet]
    public async Task<IActionResult> HandleGetServicesAsync([FromHeader(Name = "x-query")] string query, CancellationToken cancellationToken = new ())
    {
        var model = HttpContext.SafeGetListQuery<GetServices.Query, ListResultModel<ServiceDto>>(query);
        return Ok(await Mediator.Send(model, cancellationToken));
    }
}