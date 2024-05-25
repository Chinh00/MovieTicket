

using MovieTicket.Infrastructure.Auth;
using MovieTicket.Infrastructure.EfCore;
using MovieTicket.Infrastructure.Files;
using MovieTicket.Infrastructure.Logger;
using MovieTicket.Infrastructure.Security;
using MovieTicket.Infrastructure.Swagger;
using MovieTicketClient.Application;


var builder = WebApplication.CreateBuilder(args);

builder.Services.AddCors(options =>
{
    options.AddPolicy("Cors", policyBuilder =>
    {
        policyBuilder.AllowAnyHeader().AllowAnyOrigin();
    });
});
builder.Services.AddHttpContextAccessor();
builder.Services.AddSingleton<ISecurityContextAccessor, SecurityContextAccessor>();
builder.Services.AddAuthCustom(builder.Configuration);


builder.Services.AddCustomLogger();

builder.Services.AddControllers();

builder.Services.AddCustomSwagger(typeof(MovieTicketClient.Api.Controller.Anchor));

builder.Services.AddMssqlDbContext<AppBaseContext>(builder.Configuration.GetConnectionString("db")!).AddRepository(typeof(Repository<>));
builder.Services.AddAutoMapper(typeof(Anchor).Assembly);

builder.Services.AddMediatR(e => e.RegisterServicesFromAssemblies(typeof(Anchor).Assembly));


var app = builder.Build();
app.UseCors("Cors");
app.UseAuthentication();
app.UseAuthorization();
app.MapControllers();
app.UseStaticFiles();
app.UseSwagger();
app.UseSwaggerUI();

app.MapFallbackToController("Index", "Fallback");

app.Run();

