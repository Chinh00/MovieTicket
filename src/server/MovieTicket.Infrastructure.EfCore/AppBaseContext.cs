using Microsoft.EntityFrameworkCore;
using MovieTicket.Domain.Entities;

namespace MovieTicket.Infrastructure.EfCore;

public class AppBaseContext(DbContextOptions contextOptions) : DbContext(contextOptions), IFacadeResolver
{
    private const string Schema = "MovieTicket";
    public DbSet<Movie> Movies { get; init; }
    public DbSet<Category> Categories { get; init; }
    public DbSet<Reservation> Reservations { get; init; }
    public DbSet<SeatReservation> SeatReservations { get; init; }
    public DbSet<Seat> Seats { get; init; }
    public DbSet<Screening> Screenings { get; init; }
    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);
        /*Movie*/
        modelBuilder.Entity<Movie>().ToTable("Movies", Schema).HasKey(e => e.Id);
        modelBuilder.Entity<Movie>().Property(e => e.Id).HasDefaultValueSql("(newsequentialid())");
        modelBuilder.Entity<Movie>().HasIndex(e => e.Id).IsUnique();

        modelBuilder.Entity<Movie>().Property(e => e.CreatedDate).HasDefaultValueSql("getDate()");

        /*Category*/
        modelBuilder.Entity<Category>().ToTable("Categories", Schema).HasKey(e => e.Id);
        modelBuilder.Entity<Category>().Property(e => e.Id)
            .HasDefaultValueSql("(newsequentialid())");
        modelBuilder.Entity<Category>().HasIndex(e => e.Id).IsUnique();

        modelBuilder.Entity<Category>().Property(e => e.CreatedDate).HasDefaultValueSql("getDate()");


        
        /*Room*/
        modelBuilder.Entity<Room>().ToTable("Rooms", Schema).HasKey(e => e.Id);
        modelBuilder.Entity<Room>().Property(e => e.Id)
            .HasDefaultValueSql("(newsequentialid())");
        modelBuilder.Entity<Room>().HasIndex(e => e.Id).IsUnique();

        modelBuilder.Entity<Room>().Property(e => e.CreatedDate).HasDefaultValueSql("getDate()");
        
        /*Seat*/
        modelBuilder.Entity<Seat>().ToTable("Seats", Schema).HasKey(e => e.Id);
        modelBuilder.Entity<Seat>().Property(e => e.Id)
            .HasDefaultValueSql("(newsequentialid())");
        modelBuilder.Entity<Seat>().HasIndex(e => e.Id).IsUnique();

        modelBuilder.Entity<Seat>().Property(e => e.CreatedDate).HasDefaultValueSql("getDate()");
        
        /*Screening*/
        modelBuilder.Entity<Screening>().ToTable("Screenings", Schema).HasKey(e => e.Id);
        modelBuilder.Entity<Screening>().Property(e => e.Id)
            .HasDefaultValueSql("(newsequentialid())");
        modelBuilder.Entity<Screening>().HasIndex(e => e.Id).IsUnique();

        modelBuilder.Entity<Screening>().Property(e => e.CreatedDate).HasDefaultValueSql("getDate()");
        
        
        /*Reservation*/
        modelBuilder.Entity<Reservation>().ToTable("Reservations", Schema).HasKey(e => e.Id);
        modelBuilder.Entity<Reservation>().Property(e => e.Id)
            .HasDefaultValueSql("(newsequentialid())");
        modelBuilder.Entity<Reservation>().HasIndex(e => e.Id).IsUnique();

        modelBuilder.Entity<Reservation>().Property(e => e.CreatedDate).HasDefaultValueSql("getDate()");
        
        /*Seat Reservation*/
        modelBuilder.Entity<SeatReservation>().ToTable("SeatReservations", Schema).HasKey(e => e.Id);
        modelBuilder.Entity<SeatReservation>().Property(e => e.Id)
            .HasDefaultValueSql("(newsequentialid())");
        modelBuilder.Entity<SeatReservation>().HasIndex(e => e.Id).IsUnique();

        modelBuilder.Entity<SeatReservation>().Property(e => e.CreatedDate).HasDefaultValueSql("getDate()");
        
        
        /*Relationships*/
        modelBuilder.Entity<Category>().HasMany(e => e.Movies);
        
        
        
        modelBuilder.Entity<Movie>().HasMany(e => e.Screenings);
        modelBuilder.Entity<Movie>().HasMany(e => e.Categories);

        modelBuilder.Entity<Reservation>().HasMany(e => e.SeatReservations);
        modelBuilder.Entity<Reservation>().HasOne(e => e.Screening).WithMany(e => e.Reservations)
            .HasForeignKey(e => e.ScreeningId);
        modelBuilder.Entity<Reservation>().HasOne(e => e.User).WithMany(e => e.Reservations)
            .HasForeignKey(e => e.UserId);

        modelBuilder.Entity<Room>().HasMany(e => e.Screenings);
        modelBuilder.Entity<Room>().HasMany(e => e.Seats);

        modelBuilder.Entity<Seat>().HasMany(e => e.SeatReservations);


        modelBuilder.Entity<SeatReservation>().HasOne(e => e.Seat).WithMany(e => e.SeatReservations)
            .HasForeignKey(e => e.SeatId).OnDelete(DeleteBehavior.NoAction);
        modelBuilder.Entity<SeatReservation>().HasOne(e => e.Reservation).WithMany(e => e.SeatReservations).HasForeignKey(e => e.ReservationId).OnDelete(DeleteBehavior.NoAction);
        
        
    }
}