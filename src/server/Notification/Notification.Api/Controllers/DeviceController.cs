using Microsoft.AspNetCore.Mvc;
using MovieTicket.Infrastructure.Controller;
using Notification.Application;

namespace Notification.Api.Controllers;

/// <inheritdoc />
public class DeviceController : BaseController
{
    /// <summary>
    /// register device 
    /// </summary>
    /// <param name="model"></param>
    /// <param name="cancellationToken"></param>
    /// <returns></returns>
    [HttpPost]
    public async Task<IActionResult> HandleRegisterDeviceAsync(RegisterDeviceTokenModel model, CancellationToken cancellationToken)
    {
        return Ok(await Mediator.Send(new RegisterDeviceToken.Command() { CreateModel = model }, cancellationToken));
    }
}