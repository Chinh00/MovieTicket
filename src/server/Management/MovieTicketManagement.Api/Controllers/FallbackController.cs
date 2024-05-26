using Microsoft.AspNetCore.Mvc;

namespace MovieTicketManagement.Api.Controllers;

/// <inheritdoc />
public class FallbackController : Microsoft.AspNetCore.Mvc.Controller
{
    /// <summary>
    /// 
    /// </summary>
    /// <returns></returns>
    public IActionResult Index()
    {
        return PhysicalFile(Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", "index.html"), "text/html");
    }
}