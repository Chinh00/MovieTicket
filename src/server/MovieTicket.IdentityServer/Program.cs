using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using MovieTicket.IdentityServer;
using MovieTicket.IdentityServer.Data;
using MovieTicket.IdentityServer.Data.Internal;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddDbContext<AppDbContext>((provider, optionsBuilder) =>
{
    optionsBuilder.UseSqlServer(builder.Configuration.GetConnectionString("db"), contextOptionsBuilder =>
    {
        contextOptionsBuilder.EnableRetryOnFailure();
    });
});

builder.Services.AddIdentity<User, IdentityRole<Guid>>(opt =>
    {
        opt.Password.RequireNonAlphanumeric = false;
    }).AddEntityFrameworkStores<AppDbContext>()
    .AddSignInManager<SignInManager<User>>()
    .AddUserManager<UserManager>()
    .AddDefaultTokenProviders();
    


builder.Services.AddIdentityServer(options =>
    {
        options.Events.RaiseErrorEvents = true;
        options.Events.RaiseInformationEvents = true;
        options.Events.RaiseFailureEvents = true;
        options.Events.RaiseSuccessEvents = true;

    }).AddInMemoryClients(Config.Clients)
    .AddInMemoryIdentityResources(Config.IdentityResources)
    .AddInMemoryApiScopes(Config.ApiScopes)
    .AddAspNetIdentity<User>()
    .AddDeveloperSigningCredential();

builder.Services.AddHostedService<DbMigrationHostedService>();

var app = builder.Build();


app.MapGet("/", () => "Hello World!");
app.UseIdentityServer();

app.Run();