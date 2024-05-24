using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;

namespace MovieTicketManagement.IndentityServer.Data.Internal;

public class DbMigrationHostedService : IHostedService
{
    private readonly IServiceProvider _serviceProvider;

    public DbMigrationHostedService(IServiceProvider serviceProvider)
    {
        _serviceProvider = serviceProvider;
    }

    public async Task StartAsync(CancellationToken cancellationToken)
    {
        using var scope = _serviceProvider.CreateScope();
        var userManager = scope.ServiceProvider.GetRequiredService<UserManager<Admin>>();
        var dbContext = scope.ServiceProvider.GetRequiredService<IFacadeResolver>();
        await dbContext.Database.EnsureDeletedAsync(cancellationToken);
        await dbContext.Database.MigrateAsync(cancellationToken: cancellationToken);
        await userManager.CreateAsync(new Admin()
        {
            UserName = "admin",
            Email = "hanvipno9@gmail.com"
        }, "P@ssw0rd02");
    }

    public Task StopAsync(CancellationToken cancellationToken) => Task.CompletedTask;
}