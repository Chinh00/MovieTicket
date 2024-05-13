using Microsoft.EntityFrameworkCore;

namespace MovieTicket.Infrastructure.EfCore;

public static class Extensions
{
    public static IServiceCollection AddMssqlDbContext<TDbContext>(this IServiceCollection services, string conn, Action<IServiceCollection>? action = null)
        where TDbContext : DbContext
    {

        services.AddDbContext<TDbContext>((provider, builder) =>
        {
            builder.UseSqlServer(conn, optionsBuilder =>
            {
                optionsBuilder.EnableRetryOnFailure();
                optionsBuilder.UseQuerySplittingBehavior(QuerySplittingBehavior.SplitQuery);
            });
        });
        
        action?.Invoke(services);
        return services;
    }
}