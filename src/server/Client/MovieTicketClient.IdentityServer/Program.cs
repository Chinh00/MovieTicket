using FirebaseAdmin;
using Google.Apis.Auth.OAuth2;
using Microsoft.AspNetCore.Authentication.JwtBearer;
using Microsoft.AspNetCore.Identity;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.JsonWebTokens;
using Microsoft.IdentityModel.Tokens;
using MovieTicketClient.IdentityServer;
using MovieTicketClient.IdentityServer.Data;
using MovieTicketClient.IdentityServer.Data.Internal;
using Serilog;

var builder = WebApplication.CreateBuilder(args);
Log.Logger = new LoggerConfiguration().WriteTo.Console().CreateLogger();
builder.Services.AddSerilog();
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
    .AddDeveloperSigningCredential()
    .AddExtensionGrantValidator<ExternalGrantValidator>()
    .AddExtensionGrantValidator<FirebaseGrantValidator>();
    ;

builder.Services.AddAuthentication()
        .AddGoogle("Google", options =>
        {
            options.ClientId = builder.Configuration.GetValue<string>("Authentication:Google:ClientId");
            options.ClientSecret = builder.Configuration.GetValue<string>("Authentication:Google:ClientSecret");
        });

builder.Services.AddAuthentication(options =>
    {
        options.DefaultAuthenticateScheme = JwtBearerDefaults.AuthenticationScheme;
        options.DefaultChallengeScheme = JwtBearerDefaults.AuthenticationScheme;
    })
    .AddJwtBearer(options =>
    {
        options.Authority = builder.Configuration.GetValue<string>("Identity:Url");
        options.RequireHttpsMetadata = false;
        options.Audience = "api";
        options.TokenValidationParameters.ValidateAudience = false;
        options.TokenValidationParameters.ValidateIssuer = false;
        options.TokenValidationParameters.SignatureValidator = (token, parameters) => new JsonWebToken(token);
    });

builder.Services.AddScoped<IFacadeResolver>(provider => provider.GetService<AppDbContext>());
builder.Services.AddControllers();


builder.Services.AddHostedService<DbMigrationHostedService>();

var app = builder.Build();


app.MapGet("/", () => "Hello World!");
app.UseIdentityServer();
app.UseAuthentication();
app.UseAuthorization();
app.MapControllers();

FirebaseApp.Create(new AppOptions()
{
    
    Credential = GoogleCredential.FromFile("./android-5e2d0-firebase-adminsdk-p2ikp-adbe0b9280.json")
});


app.Run();