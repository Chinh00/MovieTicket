using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class User : EntityBase
{
    public virtual ICollection<Reservation> Reservations { get; init; }

}