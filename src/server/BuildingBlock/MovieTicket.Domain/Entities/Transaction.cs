using MovieTicket.Core.Domain;

namespace MovieTicket.Domain.Entities;

public class Transaction : EntityBase
{
    public Guid? ReservationId { get; set; }
    public long Total { get; set; }
    public string Message { get; set; }
    public TransactionState TransactionState { get; set; }
}