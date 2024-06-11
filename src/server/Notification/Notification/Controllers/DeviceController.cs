using Microsoft.AspNetCore.Mvc;
using Notification.Data.Entities;
using Notification.Models;
using AppContext = Notification.Data.AppContext;

namespace Notification.Controllers;


[ApiController]
[Route("/api/[controller]")]
public class DeviceController : ControllerBase
{

    private readonly AppContext _appContext;
    
    public DeviceController(AppContext appContext)
    {
        _appContext = appContext;
    }

    [HttpPost]
    public async Task<IActionResult> HandleRegisterDeviceAsync(DeviceInfoModel model, CancellationToken cancellationToken)
    {
        await _appContext.DeviceInfos.AddAsync(new DeviceInfo()
        {
            DeviceToken = model.DeviceToken,
        }, cancellationToken);
        return Ok(await _appContext.SaveChangesAsync(cancellationToken) > 0);
    }
    
}