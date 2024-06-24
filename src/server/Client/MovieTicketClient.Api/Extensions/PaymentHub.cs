using System.Text.Json;
using Microsoft.AspNetCore.SignalR;
using MovieTicket.Core.Repository;
using MovieTicket.Domain.Entities;
using MovieTicketClient.Application.Usecases.Transactions.Specs;

namespace MovieTicketClient.Api.Extensions;

public class PaymentHub : Hub
{

    private readonly IRepository<Transaction> _repository;

    public PaymentHub(IRepository<Transaction> repository)
    {
        _repository = repository;
    }


    public override async Task OnConnectedAsync()
    {
        var httpContext = Context.GetHttpContext();
        if (httpContext == null || !httpContext.Request.Query.ContainsKey("transactionId"))
        {
            Context.Abort();
            return;
        }

        var jobId = httpContext.Request.Query["transactionId"];
        Console.WriteLine($"Chinh {jobId}");
        await Groups.AddToGroupAsync(Context.ConnectionId, jobId);
    }

    public async Task RegisterPaymentTransaction()
    {
        
    }

    public async Task CheckPaymentTransaction(PaymentTransactionModel model)
    {
        var transaction = await _repository.FindOneAsync(new GetTransactionByIdSpec(model.TransactionId));
        if (transaction.Total == model.Total)
        {
            transaction.TransactionState = TransactionState.Paid;
            await _repository.EditAsync(transaction);
            await Clients.Group(model.TransactionId.ToString()).SendAsync("ConfirmPayment", transaction?.Id);
        } 
    }
    
}

public record PaymentTransactionModel
{
    public Guid TransactionId { get; set; }
    public long Total { get; set; }
}