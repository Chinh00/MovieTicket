using MovieTicket.Domain.Entities;
using MovieTicket.Infrastructure.EfCore.Fakers;

namespace MovieTicket.Infrastructure.EfCore.Internal;

public class DbMigrationDataHostService : IHostedService
{
    private readonly IServiceProvider _serviceProvider;

    public DbMigrationDataHostService(IServiceProvider serviceProvider)
    {
        _serviceProvider = serviceProvider;
    }

    public async Task StartAsync(CancellationToken cancellationToken)
    {
        using var scope = _serviceProvider.CreateScope();
        var dbContext = scope.ServiceProvider.GetService<AppBaseContext>();
        var categories = new CategoryFaker("en_US").Generate(20);
        var movies = new MovieFaker("en_US").Generate(100);
        var rooms = new List<Room>()
        {
            new Room()
            {
                RoomNumber = 1,
            },
            new Room()
            {
                RoomNumber = 2,
            },
            new Room()
            {
                RoomNumber = 3,
            }
        };
        
        
        if (dbContext != null && !dbContext.Categories.Any())
        {
            await dbContext?.Categories.AddRangeAsync(categories, cancellationToken)!;
            await dbContext?.Movies.AddRangeAsync(movies, cancellationToken)!;
            await dbContext?.Rooms.AddRangeAsync(rooms, cancellationToken)!;
            

        }

        /*if (!dbContext.Rooms.Any())
        {
            for (int i = 1; i <= 20; ++i)
            {
                for (int j = 1; j <= 30; j++)
                {
                    await dbContext.Seats.AddAsync(new Seat()
                    {
                        RowNumber = Convert.ToChar(64 + i),
                        ColNumber = Convert.ToChar(64 + j),
                        RoomId = rooms[0].Id
                    }, cancellationToken);
                }
            }
            for (int i = 1; i <= 20; ++i)
            {
                for (int j = 1; j <= 30; j++)
                {
                    await dbContext.Seats.AddAsync(new Seat()
                    {
                        RowNumber = Convert.ToChar(64 + i),
                        ColNumber = Convert.ToChar(64 + j),
                        RoomId = rooms[1].Id
                    }, cancellationToken);
                }
            }
            for (int i = 1; i <= 20; ++i)
            {
                for (int j = 1; j <= 30; j++)
                {
                    await dbContext.Seats.AddAsync(new Seat()
                    {
                        RowNumber = Convert.ToChar(64 + i),
                        ColNumber = Convert.ToChar(64 + j),
                        RoomId = rooms[2].Id
                    }, cancellationToken);
                }
            }

        }*/
        await dbContext.SaveChangesAsync(cancellationToken);
        
    }

    public Task StopAsync(CancellationToken cancellationToken)
    {
        throw new NotImplementedException();
    }
}