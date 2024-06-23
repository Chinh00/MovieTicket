using System.Linq.Expressions;
using MovieTicket.Core.Specification;
using MovieTicket.Domain.Entities;

namespace MovieTicketClient.Application.Usecases.Transactions.Specs;

public class GetTransactionByIdSpec : SpecificationBase<Transaction>
{
    private readonly Guid _guid;

    public GetTransactionByIdSpec(Guid guid)
    {
        _guid = guid;
    }

    public override Expression<Func<Transaction, bool>> Criteria => transaction => transaction.Id == _guid;
}