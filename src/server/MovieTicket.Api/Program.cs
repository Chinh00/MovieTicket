using MovieTicket.Infrastructure.EfCore;

var builder = WebApplication.CreateBuilder(args);


builder.Services.AddMssqlDbContext<AppBaseContext>(builder.Configuration.GetConnectionString("db")!);


var app = builder.Build();



app.Run();

