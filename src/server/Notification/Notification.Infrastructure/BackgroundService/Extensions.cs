using Hangfire;

namespace Notification.Infrastructure.BackgroundService;

public static class Extensions
{
    public static IServiceCollection AddHangfireExtensions(this IServiceCollection services, IConfiguration config, Action<IServiceCollection>? action = null)
    {
        services.AddHangfire(configuration => configuration
            .SetDataCompatibilityLevel(CompatibilityLevel.Version_180)
            .UseSimpleAssemblyNameTypeSerializer()
            .UseRecommendedSerializerSettings()
            .UseSqlServerStorage(config.GetConnectionString("HangfireConnection")));


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