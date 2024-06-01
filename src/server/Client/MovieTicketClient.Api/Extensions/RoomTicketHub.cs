using Microsoft.AspNetCore.SignalR;
using MovieTicket.Infrastructure.Hubs;

namespace MovieTicketClient.Api.Extensions;

/// <inheritdoc />
public class RoomTicketHub : Hub
{
    public async Task SendMessage()
    {
        await Clients.All.SendAsync("Receive");
    }
}