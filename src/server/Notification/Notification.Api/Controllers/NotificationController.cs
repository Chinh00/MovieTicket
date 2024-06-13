using Microsoft.AspNetCore.Mvc;
using MovieTicket.Infrastructure.Controller;
using Notification.Application;

namespace Notification.Api.Controllers;

/// <inheritdoc />
public class NotificationController : BaseController
{
    /// <summary>
    /// Create notification for new movie
    /// </summary>
    /// <param name="model"></param>
    /// <param name="cancellationToken"></param>
    /// <returns></returns>
    [HttpPost]
    public async Task<IActionResult> HandlePushNotificationAsync(MovieNotificationCreateModel model, CancellationToken cancellationToken)
    {
        return Ok(await Mediator.Send(new CreateMovieNotification.Command() { CreateModel = model }, cancellationToken));
    } 
    
    
}