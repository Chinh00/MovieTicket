using Serilog;

namespace MovieTicket.Infrastructure.Logger;

public static class Extensions
{
    public static IServiceCollection AddCustomLogger(this IServiceCollection services, Action<IServiceCollection>? action = null)
    {
        var logger = new LoggerConfiguration().WriteTo.Console().CreateLogger();
        Log.Logger = logger;
        services.AddSerilog();
        
        
        action?.Invoke(services);
        return services;
    }
}