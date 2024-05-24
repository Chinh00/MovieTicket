using Microsoft.IdentityModel.Tokens;

namespace MovieTicket.Infrastructure.Auth;

public static class Extensions
{
    public static IServiceCollection AddAuthCustom(this IServiceCollection services, IConfiguration config, Action<IServiceCollection>? action = null)
    {

        services.AddAuthentication().AddJwtBearer(options =>
        {
            options.Authority = config.GetSection("Identity:Url").Value;
            options.RequireHttpsMetadata = false;
            
            options.TokenValidationParameters = new TokenValidationParameters()
            {
                ValidateAudience = false
            };
        });
        action?.Invoke(services);
        return services;
    }

    
}