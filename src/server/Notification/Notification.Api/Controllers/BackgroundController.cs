using Hangfire;
using Microsoft.AspNetCore.Mvc;
using MovieTicket.Infrastructure.Controller;

namespace Notification.Api.Controllers;

/// <inheritdoc />
public class BackgroundController : BaseController
{
    [HttpPost]
    public async Task<IActionResult> HandleCreateBackgroundJob()
    {
        throw new NotImplementedException();
    }
}