using Microsoft.EntityFrameworkCore;
using MovieTicket.Core.Repository;
using MovieTicket.Domain.Entities;
using MovieTicket.Infrastructure.EfCore.Internal;

namespace MovieTicket.Infrastructure.EfCore;

public static class Extensions
{
    public static IServiceCollection AddMssqlDbContext<TDbContext>(this IServiceCollection services, string conn, Action<IServiceCollection>? action = null)
        where TDbContext : DbContext, IFacadeResolver
    {

        services.AddDbContext<TDbContext>((provider, builder) =>
        {
            builder.UseSqlServer(conn, optionsBuilder =>
            {
                optionsBuilder.EnableRetryOnFailure();
                optionsBuilder.UseQuerySplittingBehavior(QuerySplittingBehavior.SplitQuery);
            });
        });
        services.AddTransient<IFacadeResolver>(e => e.GetService<TDbContext>());
        services.AddHostedService<DbMigrateHostService>();
        services.AddHostedService<DbMigrationDataHostService>();

        action?.Invoke(services);
        return services;
    }

    public static IServiceCollection AddRepository(this IServiceCollection services, Type repoType)
    {
        services.Scan(scan => scan
            .FromAssembliesOf(repoType)
            .AddClasses(classes =>
                classes.AssignableTo(repoType)).As(typeof(IRepository<>)).WithScopedLifetime()
            .AddClasses(classes =>
                classes.AssignableTo(repoType)).As(typeof(IGridRepository<>)).WithScopedLifetime()
        );
        return services;
    }

  
}