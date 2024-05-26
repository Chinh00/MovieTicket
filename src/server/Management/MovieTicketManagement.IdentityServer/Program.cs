using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using MovieTicketManagement.IdentityServer;
using MovieTicketManagement.IdentityServer.Data;
using MovieTicketManagement.IdentityServer.Data.Internal;
using Serilog;

var builder = WebApplication.CreateBuilder(args);
Log.Logger = new LoggerConfiguration().WriteTo.Console().CreateLogger();
builder.Services.AddSerilog();
builder.Services.AddCors(options =>
{
    options.AddPolicy("Cors", policyBuilder =>
    {
        policyBuilder.AllowAnyOrigin();
    });
});
builder.Services.AddDbContext<AppDbContext>((provider, optionsBuilder) =>
{
    optionsBuilder.UseSqlServer(builder.Configuration.GetConnectionString("db"), contextOptionsBuilder =>
    {
        contextOptionsBuilder.EnableRetryOnFailure();
    });
});

builder.Services.AddIdentity<Admin, IdentityRole<Guid>>().AddEntityFrameworkStores<AppDbContext>()
    .AddSignInManager<SignInManager<Admin>>().AddUserManager<UserManager<Admin>>().AddDefaultTokenProviders();

builder.Services.AddIdentityServer(options =>
    {
        options.Events.RaiseErrorEvents = true;
        options.Events.RaiseInformationEvents = true;
        options.Events.RaiseFailureEvents = true;
        options.Events.RaiseSuccessEvents = true;

    }).AddInMemoryClients(Config.Clients)
    .AddInMemoryIdentityResources(Config.IdentityResources)
    .AddInMemoryApiScopes(Config.ApiScopes)
    .AddAspNetIdentity<Admin>()
    .AddDeveloperSigningCredential();
builder.Services.AddScoped<IFacadeResolver>(provider => provider.GetService<AppDbContext>());
builder.Services.AddHostedService<DbMigrationHostedService>();
var app = builder.Build();
app.UseCors("Cors");
app.MapGet("/", () => "Hello World!");

app.UseIdentityServer();
app.Run();