using Microsoft.EntityFrameworkCore;

namespace MovieTicketClient.Application.Data;

public class DbContextMemory : DbContext
{

    public DbContextMemory(DbContextOptions<DbContextMemory> options) : base(options)
    {
        
    }
    
    public DbSet<SeatPlace> SeatPlaces { get; set; }


    

    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);
        modelBuilder.Entity<SeatPlace>(e =>
        {
            e.ToTable("SeatPlaces");
        });
    }
}