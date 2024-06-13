using Microsoft.EntityFrameworkCore;
using Notification.Infrastructure.Data.Entities;

namespace Notification.Infrastructure.Data;

public class AppContext : DbContext
{
    public AppContext(DbContextOptions<AppContext> options) : base(options)
    {
        
    }

    public DbSet<DeviceToken> DeviceTokens { get; }
    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);

        modelBuilder.Entity<DeviceToken>(e =>
        {
            e.ToTable("DeviceTokens");
            e.HasKey(c => c.Id);
            e.Property(c => c.Id).HasDefaultValueSql("(newsequentialid())");
            e.Property(c => c.CreatedDate).HasDefaultValueSql("(getutcdate())");
        });
    }
}