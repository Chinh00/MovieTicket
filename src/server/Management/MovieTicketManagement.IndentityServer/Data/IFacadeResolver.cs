using Microsoft.EntityFrameworkCore.Infrastructure;

namespace MovieTicketManagement.IndentityServer.Data;

public interface IFacadeResolver
{
    DatabaseFacade Database { get; }
}