using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using MovieTicketManagement.IndentityServer;
using MovieTicketManagement.IndentityServer.Data;
using MovieTicketManagement.IndentityServer.Data.Internal;

var builder = WebApplication.CreateBuilder(args);

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

app.MapGet("/", () => "Hello World!");

app.UseIdentityServer();
app.Run();