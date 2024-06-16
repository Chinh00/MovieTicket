using Serilog;

var builder = WebApplication.CreateBuilder(args);


Log.Logger = new LoggerConfiguration().WriteTo.Console().CreateLogger();
builder.WebHost.ConfigureKestrel(e => e.Limits.MaxRequestBodySize = 1000 * 1024 * 1024);

builder.Services.AddSerilog();
builder.Services.AddReverseProxy().LoadFromConfig(builder.Configuration.GetSection("ReverseProxy"));

var app = builder.Build();
app.UseRouting();
app.MapReverseProxy();
app.Run();