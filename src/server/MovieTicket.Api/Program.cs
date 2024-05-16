using MovieTicket.Application;
using MovieTicket.Core.Repository;
using MovieTicket.Infrastructure.EfCore;
using MovieTicket.Infrastructure.EfCore.Internal;
using MovieTicket.Infrastructure.Logger;

var builder = WebApplication.CreateBuilder(args);

builder.Services.AddCustomLogger();

builder.Services.AddControllers();

builder.Services.AddSwaggerGen();

builder.Services.AddMssqlDbContext<AppBaseContext>(builder.Configuration.GetConnectionString("db")!).AddRepository(typeof(Repository<>));
builder.Services.AddAutoMapper(typeof(Anchor).Assembly);
builder.Services.AddHostedService<DbMigrateHostService>();
builder.Services.AddMediatR(e => e.RegisterServicesFromAssemblies(typeof(Anchor).Assembly));


var app = builder.Build();

app.MapControllers();

app.UseSwagger();
app.UseSwaggerUI();


app.Run();

