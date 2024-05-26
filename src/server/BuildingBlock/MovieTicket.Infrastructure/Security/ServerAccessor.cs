namespace MovieTicket.Infrastructure.Security;

public class ServerAccessor : IServerAccessor
{
    private readonly IHttpContextAccessor _httpContextAccessor;
    private readonly bool _isDevelopment;

    public ServerAccessor(IHttpContextAccessor httpContextAccessor, IHostEnvironment webHostEnvironment)
    {
        _httpContextAccessor = httpContextAccessor;
        _isDevelopment = webHostEnvironment.IsDevelopment();
    }

    public string GetServerDomain()
    {
        var request = _httpContextAccessor.HttpContext!.Request;
        return $"{request.Scheme}://{request.Host}";
    }

}