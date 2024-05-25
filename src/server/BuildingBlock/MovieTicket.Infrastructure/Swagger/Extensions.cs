using Microsoft.OpenApi.Models;

namespace MovieTicket.Infrastructure.Swagger;

public static class Extensions
{
    public static IServiceCollection AddCustomSwagger(this IServiceCollection services, Type anchorApi)
    {
        
        
        services.AddSwaggerGen(options =>
        {
            options.CustomSchemaIds(x => x.FullName?.Replace("+", "."));
            var xmlFilename = $"{anchorApi.Assembly.GetName().Name}.xml";
            options.IncludeXmlComments(Path.Combine(AppContext.BaseDirectory, xmlFilename));
        });
        
        return services;
    }
}