using System.Security.Claims;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using MovieTicketClient.IdentityServer.Data;

namespace MovieTicketClient.IdentityServer.Controllers;

[ApiController]
[Route("api/[controller]")]
[Authorize]
public class AccountController : Controller
{
    private readonly SignInManager<User> _signInManager;
    private readonly UserManager _userManager;
    private readonly IHttpContextAccessor _contextAccessor;
    public AccountController(SignInManager<User> signInManager, UserManager userManager, IHttpContextAccessor contextAccessor)
    {
        _signInManager = signInManager;
        _userManager = userManager;
        _contextAccessor = contextAccessor;
    }

    [HttpGet]
    public async Task<IActionResult> HandleGetUserDetail()
    {
        var userId = _contextAccessor.HttpContext?.User?.FindFirstValue(ClaimTypes.NameIdentifier);
        var user = await _userManager.FindByIdAsync(userId);
        return Ok(user);
    }
}