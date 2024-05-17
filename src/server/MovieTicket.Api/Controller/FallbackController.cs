using Microsoft.AspNetCore.Mvc;

namespace MovieTicket.Api.Controller;

public class FallbackController : Microsoft.AspNetCore.Mvc.Controller
{
    public IActionResult Index()
    {
        return PhysicalFile(Path.Combine(Directory.GetCurrentDirectory(), "wwwroot", "index.html"), "text/html");
    }
}