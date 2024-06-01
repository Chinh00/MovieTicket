
using Microsoft.OpenApi.Models;
using MovieTicket.Infrastructure.Auth;
using MovieTicket.Infrastructure.Caching;
using MovieTicket.Infrastructure.EfCore;
using MovieTicket.Infrastructure.Files;
using MovieTicket.Infrastructure.Logger;
using MovieTicket.Infrastructure.Security;
using MovieTicket.Infrastructure.Swagger;
using MovieTicketManagement.Api.Controllers;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddCustomLogger();
builder.Services.AddCors(options =>
{
    options.AddPolicy("Cors", policyBuilder =>
    {
        policyBuilder.AllowAnyHeader().AllowAnyOrigin();
    });
});

builder.Services.AddCachingService(builder.Configuration);

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

