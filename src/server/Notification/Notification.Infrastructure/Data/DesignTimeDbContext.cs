using Microsoft.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore.Design;

namespace Notification.Infrastructure.Data;

public class DesignTimeDbContext : IDesignTimeDbContextFactory<AppDbContext>
{
    public AppDbContext CreateDbContext(string[] args)
    {
        var options = new DbContextOptionsBuilder().UseSqlServer("Server=localhost;Database=Jobs;Encrypt=false;User Id=sa;Password=@P@ssw0rd02;");
        return (AppDbContext)Activator.CreateInstance(typeof(AppDbContext), options.Options);
    }
}