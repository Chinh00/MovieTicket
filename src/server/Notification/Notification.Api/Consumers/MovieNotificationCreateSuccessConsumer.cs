using MassTransit;
using MediatR;
using MovieTicket.Message.MovieNotification;

namespace Notification.Api.Consumers;

public class MovieNotificationCreateSuccessConsumer : IConsumer<MovieNotificationCreateSuccess>
{

    private readonly IMediator _mediator;
    private readonly ILogger<MovieNotificationCreateSuccessConsumer> _logger;
    public MovieNotificationCreateSuccessConsumer(IMediator mediator, ILogger<MovieNotificationCreateSuccessConsumer> logger)
    {
        _mediator = mediator;
        _logger = logger;
    }

    public async Task Consume(ConsumeContext<MovieNotificationCreateSuccess> context)
    {
        _logger.LogInformation(context.Message.Movie.ToString());
        await _mediator.Publish(context.Message);
    }
}