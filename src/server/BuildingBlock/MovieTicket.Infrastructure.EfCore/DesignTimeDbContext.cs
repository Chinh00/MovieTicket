using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Design;

namespace MovieTicket.Infrastructure.EfCore;

public class DesignTimeDbContext : IDesignTimeDbContextFactory<AppBaseContext>
{
    public AppBaseContext CreateDbContext(string[] args)
    {
        var options = new DbContextOptionsBuilder().UseSqlServer("Server=localhost;Database=movie_ticket;Encrypt=false;User Id=sa;Password=@P@ssw0rd02;");
        return (AppBaseContext)Activator.CreateInstance(typeof(AppBaseContext), options.Options);
    }
}