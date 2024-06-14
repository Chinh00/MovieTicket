using Microsoft.EntityFrameworkCore;

namespace Notification.Infrastructure.Data.Internal;

public class DbMigrationsHostService : IHostedService
{
    
    private readonly IServiceProvider _serviceProvider;

    public DbMigrationsHostService(IServiceProvider serviceProvider)
    {
        _serviceProvider = serviceProvider;
    }

    public async Task StartAsync(CancellationToken cancellationToken)
    {
        using var scope = _serviceProvider.CreateScope();
        var dbContext = scope.ServiceProvider.GetService<AppDbContext>();
        
        await dbContext?.Database.MigrateAsync(cancellationToken: cancellationToken)!;
    }

    public Task StopAsync(CancellationToken cancellationToken)
    {
        throw new NotImplementedException();
    }
}