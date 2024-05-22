using Microsoft.AspNetCore.Identity;
using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class User : IdentityUser<Guid>
{
    public virtual ICollection<Reservation> Reservations { get; init; }

}