using Microsoft.EntityFrameworkCore;
using Notification.Data.Entities;

namespace Notification.Data;

public class AppContext : DbContext
{

    public AppContext(DbContextOptions<AppContext> options) : base(options)
    {
        
    }
    
    public DbSet<DeviceInfo> DeviceInfos { get; set; }
    protected override void OnModelCreating(ModelBuilder modelBuilder)
    {
        base.OnModelCreating(modelBuilder);
        modelBuilder.Entity<DeviceInfo>(e =>
        {
            e.ToTable("DeviceInfos");
            e.Property(t => t.Id).HasDefaultValueSql("(newsequentialid())");
            e.HasIndex(t => t.Id).IsUnique();

            e.Property(t => t.CreatedDate).HasDefaultValueSql("(getutcdate())");
        });
        
    }
}