using Hangfire;
using Hangfire.Redis.StackExchange;
using Hangfire.SqlServer;
using Microsoft.Data.SqlClient;

namespace Notification.Infrastructure.BackgroundService;

public static class Extensions
{
    public static IServiceCollection AddHangfireExtensions(this IServiceCollection services, IConfiguration config, Action<IServiceCollection>? action = null)
    {
     
        services.AddHangfire(configuration => configuration.UseRedisStorage(config.GetValue<string>("Redis:Url")));
        services.AddHangfireServer();

        services.AddMvc();

        
        action?.Invoke(services);
        return services;
    }
    
    
    

    public static IApplicationBuilder UseHangfireUi(this WebApplication app)
    {
        
        app.UseStaticFiles();
        app.UseHangfireDashboard();
        app.UseHangfireServer();

        app.MapControllers();
        app.UseHangfireDashboard("/hangfire", new DashboardOptions
        {
   
        });
        return app;
    }
}