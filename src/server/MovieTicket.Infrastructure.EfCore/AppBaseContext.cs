using Microsoft.EntityFrameworkCore;
using MovieTicket.Domain.Entities;

namespace MovieTicket.Infrastructure.EfCore;

public class AppBaseContext(DbContextOptions<AppBaseContext> contextOptions) : DbContext(contextOptions)
{
    private const string Schema = "MovieTicket";
    public DbSet<Movie> Movies { get; init; }
    public DbSet<Category> Categories { get; init; }
    
    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);
        /*Movie*/
        modelBuilder.Entity<Movie>().ToTable("Movies", Schema).HasKey(e => e.Id);
        modelBuilder.Entity<Movie>().Property(e => e.Id).HasColumnType("uuid").HasDefaultValueSql("(newsequentialid())");
        modelBuilder.Entity<Movie>().HasIndex(e => e.Id).IsUnique();

        modelBuilder.Entity<Movie>().Property(e => e.CreatedDate).HasDefaultValueSql("now()");
        modelBuilder.Entity<Movie>().HasMany(e => e.Categories).WithMany(e => e.Movies);

        /*Category*/
        modelBuilder.Entity<Category>().ToTable("Categories", Schema).HasKey(e => e.Id);
        modelBuilder.Entity<Category>().Property(e => e.Id).HasColumnType("uuid")
            .HasDefaultValueSql("(newsequentialid())");
        modelBuilder.Entity<Category>().HasIndex(e => e.Id).IsUnique();

        modelBuilder.Entity<Category>().Property(e => e.CreatedDate).HasDefaultValueSql("now");

        modelBuilder.Entity<Category>().HasMany(e => e.Movies).WithMany(e => e.Categories);

    }
}