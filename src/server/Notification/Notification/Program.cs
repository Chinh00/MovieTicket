using Hangfire;
using Microsoft.EntityFrameworkCore;
using MovieTicket.Infrastructure.Logger;
using AppContext = Notification.Data.AppContext;

var builder = WebApplication.CreateBuilder(args);


builder.Services.AddCustomLogger();
builder.Services.AddHangfire(configuration => configuration
    .SetDataCompatibilityLevel(CompatibilityLevel.Version_180)
    .UseSimpleAssemblyNameTypeSerializer()
    .UseRecommendedSerializerSettings()
    .UseSqlServerStorage(builder.Configuration.GetConnectionString("HangfireConnection")));

builder.Services.AddDbContext<AppContext>((provider, optionsBuilder) =>
{
    optionsBuilder.UseSqlServer(builder.Configuration.GetConnectionString("HangfireConnection"));
});

builder.Services.AddHangfireServer();

builder.Services.AddMvc();
builder.Services.AddControllers();

builder.Services.AddSwaggerGen();

var app = builder.Build();

app.UseSwagger();
app.UseSwaggerUI();

app.MapControllers();

app.UseStaticFiles();
app.UseHangfireDashboard();

app.MapControllers();
app.UseHangfireDashboard("/hangfire", new DashboardOptions
{
   
});


app.Run();