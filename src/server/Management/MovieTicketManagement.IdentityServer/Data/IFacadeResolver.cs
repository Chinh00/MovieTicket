using Microsoft.EntityFrameworkCore.Infrastructure;

namespace MovieTicketManagement.IdentityServer.Data;

public interface IFacadeResolver
{
    DatabaseFacade Database { get; }
}