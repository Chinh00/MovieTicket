using System.Security.Claims;

namespace MovieTicket.Infrastructure.Auth;

public class SecurityContextAccessor : ISecurityContextAccessor
{
    private readonly IHttpContextAccessor _httpContextAccessor;

    public SecurityContextAccessor(IHttpContextAccessor httpContextAccessor)
    {
        _httpContextAccessor = httpContextAccessor;
    }

    public object GetUserId()
    {
        return _httpContextAccessor?.HttpContext?.User.FindFirstValue(ClaimTypes.NameIdentifier) ?? Guid.Empty.ToString();
    }
}