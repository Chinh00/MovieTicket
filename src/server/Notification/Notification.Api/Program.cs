using Confluent.Kafka;
using FirebaseAdmin;
using Google.Apis.Auth.OAuth2;
using MassTransit;
using Microsoft.EntityFrameworkCore;
using MovieTicket.Infrastructure.Logger;
using MovieTicket.Message.MovieNotification;
using Notification.Api.Consumers;
using Notification.Application;
using Notification.Infrastructure.BackgroundService;
using Notification.Infrastructure.Data;
using Notification.Infrastructure.Data.Internal;
using Notification.Infrastructure.Firebase;

var builder = WebApplication.CreateBuilder(args);
builder.Services.AddCors(options =>
{
    options.AddPolicy("Cors", policyBuilder =>
    {
        policyBuilder.AllowAnyHeader().AllowAnyMethod().AllowAnyOrigin();
    });
});
builder.Services.AddCustomLogger();

builder.Services.AddMediatR(e => e.RegisterServicesFromAssembly(typeof(Anchor).Assembly));
builder.Services.AddControllers();
builder.Services.AddSwaggerGen();
builder.Services.AddDbContext<AppDbContext>((provider, optionsBuilder) =>
{
    optionsBuilder.UseSqlServer(builder.Configuration.GetConnectionString("HangfireConnection"),
        contextOptionsBuilder =>
        {
            contextOptionsBuilder.EnableRetryOnFailure().EnableRetryOnFailure()
                .UseQuerySplittingBehavior(QuerySplittingBehavior.SingleQuery);
        });
});
//builder.Services.AddHostedService<DbMigrationsHostService>();

builder.Services.AddHangfireExtensions(builder.Configuration);
builder.WebHost.ConfigureKestrel(e => e.Limits.MaxRequestBodySize = 100 * 1024 * 1024);
builder.Services.AddMassTransit(c =>
{
    c.SetKebabCaseEndpointNameFormatter();;
    c.UsingInMemory((context, configurator) =>
    {
        configurator.ConfigureEndpoints(context);
    });
            
    c.AddRider(t =>
    {   
        t.AddProducer<MovieNotificationCreate>(nameof(MovieNotificationCreate));
        t.AddConsumer<MovieNotificationCreateSuccessConsumer>();
        t.AddConsumer<MovieNotificationCreateFailConsumer>();
        t.UsingKafka((context, configurator) =>
        {
            configurator.Host(builder.Configuration.GetValue<string>("Kafka:Url"));
            configurator.TopicEndpoint<Null, MovieNotificationCreateSuccess>(nameof(MovieNotificationCreateSuccess), "Movie",
                opt =>
                {
                    opt.AutoOffsetReset = AutoOffsetReset.Earliest;
                    opt.CreateIfMissing(options => options.NumPartitions = 1);
                    opt.ConfigureConsumer<MovieNotificationCreateSuccessConsumer>(context);
                });
            configurator.TopicEndpoint<Null, MovieNotificationCreateFail>(nameof(MovieNotificationCreateFail), "Movie",
                opt =>
                {
                    opt.AutoOffsetReset = AutoOffsetReset.Earliest;
                    opt.CreateIfMissing(options => options.NumPartitions = 1);
                    opt.ConfigureConsumer<MovieNotificationCreateFailConsumer>(context);
                });
        });
                

    });
});
builder.Services.AddScoped<IFirebaseNotificationService, FirebaseNotificationService>();

var app = builder.Build();
app.UseCors("Cors");
app.UseSwagger();
app.UseSwaggerUI();
app.MapControllers();
app.UseHangfireUi();

FirebaseApp.Create(new AppOptions()
{
    
    Credential = GoogleCredential.FromFile("./android-5e2d0-firebase-adminsdk-p2ikp-adbe0b9280.json")
});


app.Run();

