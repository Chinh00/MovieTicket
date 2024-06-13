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
using Notification.Infrastructure.Firebase;
using AppContext = Notification.Infrastructure.Data.AppContext;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddCustomLogger();

builder.Services.AddHangfireExtensions(builder.Configuration);
builder.Services.AddMediatR(e => e.RegisterServicesFromAssembly(typeof(Anchor).Assembly));
builder.Services.AddControllers();
builder.Services.AddSwaggerGen();
builder.Services.AddDbContext<AppContext>((provider, optionsBuilder) =>
{
    optionsBuilder.UseSqlServer(builder.Configuration.GetConnectionString("HangfireConnection"));
});

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
            configurator.Host("localhost:9092");
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
builder.Services.AddSingleton<IFirebaseNotificationService, FirebaseNotificationService>();
FirebaseApp.Create(new AppOptions()
{
    Credential = GoogleCredential.FromFile("./android-5e2d0-firebase-adminsdk-p2ikp-adbe0b9280.json")
});
var app = builder.Build();
app.UseSwagger();
app.UseSwaggerUI();
app.MapControllers();
app.UseHangfireUi();

app.Run();