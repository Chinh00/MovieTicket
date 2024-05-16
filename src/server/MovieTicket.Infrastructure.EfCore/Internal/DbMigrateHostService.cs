using Microsoft.EntityFrameworkCore;

namespace MovieTicket.Infrastructure.EfCore.Internal;

public class DbMigrateHostService : IHostedService
{
    private readonly ILogger<DbMigrateHostService> _logger;
    private readonly IServiceProvider _serviceProvider;
    public DbMigrateHostService(ILogger<DbMigrateHostService> logger, IServiceProvider serviceProvider)
    {
        _logger = logger;
        _serviceProvider = serviceProvider;
    }

    public async Task StartAsync(CancellationToken cancellationToken)
    {
        using var scope = _serviceProvider.CreateScope();
        var dbContext = scope.ServiceProvider.GetService<IFacadeResolver>();
        await dbContext.Database.MigrateAsync(cancellationToken: cancellationToken)!;
        
    }

    public Task StopAsync(CancellationToken cancellationToken) => Task.CompletedTask;
}