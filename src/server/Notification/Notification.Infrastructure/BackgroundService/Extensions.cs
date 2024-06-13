using Hangfire;
using Hangfire.SqlServer;

namespace Notification.Infrastructure.BackgroundService;

public static class Extensions
{
    public static IServiceCollection AddHangfireExtensions(this IServiceCollection services, IConfiguration config, Action<IServiceCollection>? action = null)
    {
        services.AddHangfire(configuration => configuration
            .SetDataCompatibilityLevel(CompatibilityLevel.Version_180)
            .UseSimpleAssemblyNameTypeSerializer()
            .UseRecommendedSerializerSettings()
            .UseSqlServerStorage(config.GetConnectionString("HangfireConnection") , new SqlServerStorageOptions
            {
                CommandBatchMaxTimeout = TimeSpan.FromMinutes(5),
                SlidingInvisibilityTimeout = TimeSpan.FromMinutes(5),
                QueuePollInterval = TimeSpan.Zero,
                UseRecommendedIsolationLevel = true,
                UsePageLocksOnDequeue = true,
                DisableGlobalLocks = true
            })) ;


        services.AddHangfireServer();

        services.AddMvc();

        
        action?.Invoke(services);
        return services;
    }

    public static IApplicationBuilder UseHangfireUi(this WebApplication app)
    {
        
        app.UseStaticFiles();
        app.UseHangfireDashboard();

        app.MapControllers();
        app.UseHangfireDashboard("/hangfire", new DashboardOptions
        {
   
        });
        return app;
    }
}