using Microsoft.AspNetCore.Mvc;
using MovieTicket.Core.Domain;
using MovieTicket.Infrastructure;
using MovieTicket.Infrastructure.Controller;
using MovieTicketManagement.Application.Usecases.ServiceCRUD;

namespace MovieTicketManagement.Api.Controllers;

/// <inheritdoc />
public class ServiceController : BaseController
{
    /// <summary>
    /// Get Movies
    /// </summary>
    /// <param name="query"></param>
    /// <param name="cancellationToken"></param>
    /// <returns></returns>
    [HttpGet]
    public async Task<IActionResult> HandleGetServicesAsync([FromHeader(Name = "x-query")] string query, CancellationToken cancellationToken = new ())
    {
        var model = HttpContext.SafeGetListQuery<GetServices.Query, ListResultModel<ServiceDto>>(query);
        return Ok(await Mediator.Send(model, cancellationToken ));
    }
    
    /// <summary>
    /// Create service
    /// </summary>
    /// <param name="model"></param>
    /// <param name="cancellationToken"></param>
    /// <returns></returns>
    [HttpPost]
    public async Task<IActionResult> HandleCreateServiceAsync(ServiceCreateModel model, CancellationToken cancellationToken = new ())
    {
        return Ok(await Mediator.Send(new CreateService.Command() {CreateModel = model}, cancellationToken));
    }
}