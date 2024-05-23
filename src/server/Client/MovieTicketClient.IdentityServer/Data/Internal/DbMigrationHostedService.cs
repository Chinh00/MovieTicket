using Microsoft.EntityFrameworkCore;

namespace MovieTicketClient.IdentityServer.Data.Internal;

public class DbMigrationHostedService : IHostedService
{
    private readonly IServiceProvider _serviceProvider;

    public DbMigrationHostedService(IServiceProvider serviceProvider)
    {
        _serviceProvider = serviceProvider;
    }

    public async Task StartAsync(CancellationToken cancellationToken)
    {
        using var scope = _serviceProvider.GetRequiredService<IServiceScopeFactory>().CreateScope();
        var userManager = scope.ServiceProvider.GetRequiredService<UserManager>();
        var dbContext = scope.ServiceProvider.GetRequiredService<AppDbContext>();
        await dbContext.Database.MigrateAsync(cancellationToken: cancellationToken);
        await userManager.CreateAsync(new User()
        {
            UserName = "admin",
            Email = "hanvipno9@gmail.com"
        }, "P@ssw0rd02");
    }

    public Task StopAsync(CancellationToken cancellationToken) => Task.CompletedTask;
}