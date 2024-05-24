using Microsoft.EntityFrameworkCore.Infrastructure;

namespace MovieTicketClient.IdentityServer.Data;

public interface IFacadeResolver
{
    DatabaseFacade Database { get; }
}