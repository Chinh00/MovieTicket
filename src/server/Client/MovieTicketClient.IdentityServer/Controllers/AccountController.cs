using System.Security.Claims;
using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Mvc;
using MovieTicket.Infrastructure.Files;
using MovieTicketClient.IdentityServer.Data;
using MovieTicketClient.IdentityServer.Models;

namespace MovieTicketClient.IdentityServer.Controllers;

[ApiController]
[Route("api/[controller]")]
[Authorize]
public class AccountController : Controller
{
    private readonly SignInManager<User> _signInManager;
    private readonly UserManager _userManager;
    private readonly IHttpContextAccessor _contextAccessor;
    private readonly IFileHelper _fileHelper;
    public AccountController(SignInManager<User> signInManager, UserManager userManager, IHttpContextAccessor contextAccessor, IFileHelper fileHelper)
    {
        _signInManager = signInManager;
        _userManager = userManager;
        _contextAccessor = contextAccessor;
        _fileHelper = fileHelper;
    }

    [HttpGet]
    public async Task<IActionResult> HandleGetUserDetail()
    {
        var userId = _contextAccessor.HttpContext?.User?.FindFirstValue(ClaimTypes.NameIdentifier);
        var user = await _userManager.FindByIdAsync(userId);
        return Ok(user);
    }

    [HttpPost]
    public async Task<IActionResult> HandleUpdateUserAsync(UserUpdateModel model, CancellationToken cancellationToken = new CancellationToken())
    {
        var userId = _contextAccessor.HttpContext?.User?.FindFirstValue(ClaimTypes.NameIdentifier);
        Console.WriteLine(model);
        var user = await _userManager.FindByIdAsync(userId);
        user.FullName = model.FullName;
        user.Birthday = model.Birthday;
        user.Avatar = model.Avatar;
        user.UserGender = model.UserGender;
        await _userManager.UpdateAsync(user);
        return Ok(user);
    }

    [HttpPost("update-password")]
    public async Task<IActionResult> HandleUpdatePasswordAsync(UserUpdatePasswordModel model, CancellationToken cancellationToken = new CancellationToken())
    {
        var userId = _contextAccessor.HttpContext?.User?.FindFirstValue(ClaimTypes.NameIdentifier);
        var user = await _userManager.FindByIdAsync(userId);
        await _userManager.AddPasswordAsync(user, model.NewPassword);
        return Ok(user);
    }
    
}