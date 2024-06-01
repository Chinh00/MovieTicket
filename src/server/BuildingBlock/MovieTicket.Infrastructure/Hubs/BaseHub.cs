using Microsoft.AspNetCore.SignalR;

namespace MovieTicket.Infrastructure.Hubs;

public class BaseHub : Hub
{
    private readonly ILogger<BaseHub> _logger;

    public BaseHub(ILogger<BaseHub> logger)
    {
        _logger = logger;
    }

    public override Task OnConnectedAsync()
    {
        return base.OnConnectedAsync();
        
    }
}