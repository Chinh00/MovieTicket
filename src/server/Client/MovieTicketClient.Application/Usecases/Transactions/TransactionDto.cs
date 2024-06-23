using AutoMapper;
using MovieTicket.Domain.Entities;

namespace MovieTicketClient.Application.Usecases.Transactions;

public class TransactionDto
{
    public Guid? ReservationId { get; set; }
    public long Total { get; set; }
    public string Message { get; set; }
    public TransactionState TransactionState { get; set; }
}

class TransactionMapperConfig : Profile
{
    public TransactionMapperConfig()
    {
        CreateMap<Transaction, TransactionDto>();
    }
}