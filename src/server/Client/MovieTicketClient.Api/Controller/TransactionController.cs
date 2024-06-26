using Microsoft.AspNetCore.Mvc;
using MovieTicket.Infrastructure.Controller;
using MovieTicketClient.Application.Usecases.Transactions;

namespace MovieTicketClient.Api.Controller;

/// <inheritdoc />
public class TransactionController : BaseController
{
    /// <summary>
    /// Register transaction
    /// </summary>
    /// <param name="model"></param>
    /// <param name="cancellationToken"></param>
    /// <returns></returns>
    [HttpPost]
    public async Task<IActionResult> HandleTransactionCreateAsync(TransactionCreateModel model, CancellationToken cancellationToken = new CancellationToken())
    {
        return Ok(await Mediator.Send(new TransactionCreate.Command() { CreateModel = model }, cancellationToken));
    }
}