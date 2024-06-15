
using Confluent.Kafka;
using MassTransit;
using Microsoft.OpenApi.Models;
using MovieTicket.Infrastructure.Auth;
using MovieTicket.Infrastructure.Caching;
using MovieTicket.Infrastructure.EfCore;
using MovieTicket.Infrastructure.Files;
using MovieTicket.Infrastructure.Logger;
using MovieTicket.Infrastructure.Security;
using MovieTicket.Infrastructure.Swagger;
using MovieTicket.Message.MovieNotification;
using MovieTicketManagement.Api.Consumers;
using MovieTicketManagement.Api.Controllers;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddCustomLogger();
builder.Services.AddCors(options =>
{
    options.AddPolicy("Cors", policyBuilder =>
    {
        policyBuilder.AllowAnyHeader().AllowAnyOrigin().AllowAnyMethod();
    });
});

builder.Services.AddCachingService(builder.Configuration);
builder.WebHost.ConfigureKestrel(e => e.Limits.MaxRequestBodySize = 1000 * 1024 * 1024);

builder.Services.AddMssqlDbContext<AppBaseContext>(builder.Configuration.GetConnectionString("db")).AddRepository(typeof(Repository<>));

builder.Services.AddHttpContextAccessor();
builder.Services.AddSingleton<ISecurityContextAccessor, SecurityContextAccessor>();
builder.Services.AddSingleton<IServerAccessor, ServerAccessor>();
builder.Services.AddScoped<IFileHelper, FileHelper>();
builder.Services.AddAuthCustom(builder.Configuration).AddAuthorization();
builder.Services.AddControllers();

builder.Services.AddCustomSwagger(typeof(Anchor));
builder.Services.AddAutoMapper(typeof(MovieTicketManagement.Application.Anchor).Assembly);


builder.Services.AddMediatR(e => e.RegisterServicesFromAssemblies(typeof(MovieTicketManagement.Application.Anchor).Assembly));


builder.Services.AddEndpointsApiExplorer();
builder.Services.AddMassTransit(x =>
{
    x.SetKebabCaseEndpointNameFormatter();
    x.UsingInMemory((context, configurator) =>
    {
        configurator.ConfigureEndpoints(context);
    });
    x.AddRider(e =>
    {
        
        e.AddProducer<MovieNotificationCreateSuccess>(nameof(MovieNotificationCreateSuccess));
        e.AddProducer<MovieNotificationCreateFail>(nameof(MovieNotificationCreateFail));
        e.AddConsumer<MovieNotificationCreateConsumer>();
        e.UsingKafka((context, configurator) =>
        {
            configurator.Host(builder.Configuration.GetValue<string>("Kafka:Url"));
            configurator.TopicEndpoint<Null, MovieNotificationCreate>(nameof(MovieNotificationCreate), "Movie", c =>
            {
                c.AutoOffsetReset = AutoOffsetReset.Earliest;
                c.CreateIfMissing(options => options.NumPartitions = 1);
                c.ConfigureConsumer<MovieNotificationCreateConsumer>(context);
            });
        });
    });
    
    
});



var app = builder.Build();

app.UseSwagger();
app.UseSwaggerUI();



app.UseCors("Cors");
app.UseAuthentication();
app.UseAuthorization();
app.MapControllers();

app.UseStaticFiles();

app.MapFallbackToController("Index", "Fallback");
app.Run();

