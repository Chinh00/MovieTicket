using Microsoft.AspNetCore.SignalR;
using MovieTicket.Infrastructure.Hubs;

namespace MovieTicketClient.Api.Extensions;

/// <inheritdoc />
public class RoomTicketHub : Hub
{
    private readonly ILogger<RoomTicketHub> _logger;

    public RoomTicketHub(ILogger<RoomTicketHub> logger)
    {
        _logger = logger;
    }

    public async Task SendMessage()
    {
        _logger.LogInformation(Context.ConnectionId);   
        await Clients.All.SendAsync("Receive", Context.ConnectionId);
    }
}