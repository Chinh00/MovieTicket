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
    public DbSet<Room> Rooms { get; init; } 
    
   public DbSet<Service> Services { get; init; }
   public DbSet<ServiceReservation> ServiceReservations { get; init; }
    
    
    
    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);
        /*Movie*/
        modelBuilder.Entity<Movie>().ToTable("Movies", Schema).HasKey(e => e.Id);
        modelBuilder.Entity<Movie>().Property(e => e.Id).HasDefaultValueSql("(newsequentialid())");
        modelBuilder.Entity<Movie>().HasIndex(e => e.Id).IsUnique();

        modelBuilder.Entity<Movie>().Property(e => e.CreatedDate).HasDefaultValueSql("(getutcdate())");

        /*Category*/
        modelBuilder.Entity<Category>().ToTable("Categories", Schema).HasKey(e => e.Id);
        modelBuilder.Entity<Category>().Property(e => e.Id)
            .HasDefaultValueSql("(newsequentialid())");
        modelBuilder.Entity<Category>().HasIndex(e => e.Id).IsUnique();

        modelBuilder.Entity<Category>().Property(e => e.CreatedDate).HasDefaultValueSql("(getutcdate())");


        
        /*Room*/
        modelBuilder.Entity<Room>().ToTable("Rooms", Schema).HasKey(e => e.Id);
        modelBuilder.Entity<Room>().Property(e => e.Id)
            .HasDefaultValueSql("(newsequentialid())");
        modelBuilder.Entity<Room>().HasIndex(e => e.Id).IsUnique();

        modelBuilder.Entity<Room>().Property(e => e.CreatedDate).HasDefaultValueSql("(getutcdate())");
        
        /*Seat*/
        modelBuilder.Entity<Seat>().ToTable("Seats", Schema).HasKey(e => e.Id);
        modelBuilder.Entity<Seat>().Property(e => e.Id)
            .HasDefaultValueSql("(newsequentialid())");
        modelBuilder.Entity<Seat>().HasIndex(e => e.Id).IsUnique();

        modelBuilder.Entity<Seat>().Property(e => e.CreatedDate).HasDefaultValueSql("(getutcdate())");
        
        /*Screening*/
        modelBuilder.Entity<Screening>().ToTable("Screenings", Schema).HasKey(e => e.Id);
        modelBuilder.Entity<Screening>().Property(e => e.Id)
            .HasDefaultValueSql("(newsequentialid())");
        modelBuilder.Entity<Screening>().HasIndex(e => e.Id).IsUnique();

        modelBuilder.Entity<Screening>().Property(e => e.CreatedDate).HasDefaultValueSql("(getutcdate())");
        
        
        /*Reservation*/
        modelBuilder.Entity<Reservation>().ToTable("Reservations", Schema).HasKey(e => e.Id);
        modelBuilder.Entity<Reservation>().Property(e => e.Id)
            .HasDefaultValueSql("(newsequentialid())");
        modelBuilder.Entity<Reservation>().HasIndex(e => e.Id).IsUnique();

        modelBuilder.Entity<Reservation>().Property(e => e.CreatedDate).HasDefaultValueSql("(getutcdate())");
        
        /*Seat Reservation*/
        modelBuilder.Entity<SeatReservation>().ToTable("SeatReservations", Schema).HasKey(e => e.Id);
        modelBuilder.Entity<SeatReservation>().Property(e => e.Id)
            .HasDefaultValueSql("(newsequentialid())");
        modelBuilder.Entity<SeatReservation>().HasIndex(e => e.Id).IsUnique();

        modelBuilder.Entity<SeatReservation>().Property(e => e.CreatedDate).HasDefaultValueSql("(getutcdate())");
        
        
        /*Service*/
        modelBuilder.Entity<Service>().ToTable("Services", Schema).HasKey(e => e.Id);
        modelBuilder.Entity<Service>().Property(e => e.Id)
            .HasDefaultValueSql("(newsequentialid())");
        modelBuilder.Entity<Service>().HasIndex(e => e.Id).IsUnique();

        modelBuilder.Entity<Service>().Property(e => e.CreatedDate).HasDefaultValueSql("(getutcdate())");

        /*ServiceReservation*/
        modelBuilder.Entity<ServiceReservation>().ToTable("ServiceReservations", Schema).HasKey(e => e.Id);
        modelBuilder.Entity<ServiceReservation>().Property(e => e.Id)
            .HasDefaultValueSql("(newsequentialid())");
        modelBuilder.Entity<ServiceReservation>().HasIndex(e => e.Id).IsUnique();

        modelBuilder.Entity<ServiceReservation>().Property(e => e.CreatedDate).HasDefaultValueSql("(getutcdate())");

        
        
        /*Relationships*/
        
        
        
        modelBuilder.Entity<Movie>().HasMany(e => e.Screenings).WithOne(e => e.Movie).HasForeignKey(e => e.MovieId);
        modelBuilder.Entity<Movie>().HasMany(e => e.Categories).WithMany(e => e.Movies);

        modelBuilder.Entity<Reservation>().HasMany(e => e.SeatReservations);
        modelBuilder.Entity<Reservation>().HasOne(e => e.Screening).WithMany(e => e.Reservations)
            .HasForeignKey(e => e.ScreeningId);
        

        modelBuilder.Entity<Room>().HasMany(e => e.Screenings).WithOne(e => e.Room).HasForeignKey(e => e.RoomId);
        modelBuilder.Entity<Room>().HasMany(e => e.Seats).WithOne(e => e.Room).HasForeignKey(e => e.RoomId);

        modelBuilder.Entity<Seat>().HasMany(e => e.SeatReservations).WithOne(e => e.Seat).HasForeignKey(e => e.SeatId);


        modelBuilder.Entity<SeatReservation>().HasOne(e => e.Seat).WithMany(e => e.SeatReservations)
            .HasForeignKey(e => e.SeatId).OnDelete(DeleteBehavior.NoAction);
        modelBuilder.Entity<SeatReservation>().HasOne(e => e.Reservation).WithMany(e => e.SeatReservations).HasForeignKey(e => e.ReservationId)
            .OnDelete(DeleteBehavior.NoAction);



        modelBuilder.Entity<ServiceReservation>().HasOne(e => e.Service).WithMany(e => e.ServiceReservations)
            .HasForeignKey(e => e.ServiceId);
        modelBuilder.Entity<ServiceReservation>().HasOne(e => e.Reservation).WithMany(e => e.ServiceReservations)
            .HasForeignKey(e => e.ReservationId);
    }
}