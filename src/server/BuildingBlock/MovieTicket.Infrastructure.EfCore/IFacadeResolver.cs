using Microsoft.EntityFrameworkCore.Infrastructure;

namespace MovieTicket.Infrastructure.EfCore;

public interface IFacadeResolver
{
    DatabaseFacade Database { get; }
}