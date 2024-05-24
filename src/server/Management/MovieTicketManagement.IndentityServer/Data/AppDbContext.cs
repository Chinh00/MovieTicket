using Microsoft.AspNetCore.Identity;
using Microsoft.AspNetCore.Identity.EntityFrameworkCore;
using Microsoft.EntityFrameworkCore;

namespace MovieTicketManagement.IndentityServer.Data;

public class AppDbContext : IdentityDbContext<Admin, IdentityRole<Guid>, Guid>, IFacadeResolver
{
    public AppDbContext(DbContextOptions<AppDbContext> options) : base(options)
    {
        
    }

    protected override void OnModelCreating(ModelBuilder builder)
    {
        base.OnModelCreating(builder);
        builder.Entity<Admin>().ToTable("Admins");
    }
}