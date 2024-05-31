using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.IdentityModel.JsonWebTokens;

namespace MovieTicket.Infrastructure.Auth;

public static class Extensions
{
    public static IServiceCollection AddAuthCustom(this IServiceCollection services, IConfiguration config, Action<IServiceCollection>? action = null)
    {

        services.AddAuthentication(JwtBearerDefaults.AuthenticationScheme).AddJwtBearer(options =>
        {
            options.Authority = config.GetSection("Identity:Url").Value;
            options.RequireHttpsMetadata = false;
            options.Audience = "api";
            options.TokenValidationParameters.ValidateAudience = false;
            options.TokenValidationParameters.ValidateIssuer = false;
            options.TokenValidationParameters.SignatureValidator = (token, parameters) => new JsonWebToken(token);


        });
        action?.Invoke(services);
        return services;
    }

    
}