using AutoMapper;
using MediatR;
using MovieTicket.Core.Domain;
using MovieTicket.Core.Repository;
using MovieTicket.Domain.Entities;

namespace MovieTicketClient.Application.Usecases.Transactions;


public class TransactionCreateModel
{
    public long Total { get; set; }
}

public class TransactionCreate
{
    public record Command : ICreateCommand<TransactionCreateModel, TransactionDto>
    {
        public TransactionCreateModel CreateModel { get; init; }
    }
}

public class TransactionEndpoint : IRequestHandler<TransactionCreate.Command, ResultModel<TransactionDto>>
{
    private readonly IRepository<Transaction> _repository;
    private readonly IMapper _mapper;
    public TransactionEndpoint(IRepository<Transaction> repository, IMapper mapper)
    {
        _repository = repository;
        _mapper = mapper;
    }

    public async Task<ResultModel<TransactionDto>> Handle(TransactionCreate.Command request, CancellationToken cancellationToken)
    {
        return ResultModel<TransactionDto>.Create(_mapper.Map<TransactionDto>(
            await _repository.AddAsync(new Transaction() {
                Message = Guid.NewGuid().ToString(),
                Total = request.CreateModel.Total
                })
        ));
    }
}