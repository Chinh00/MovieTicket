using MassTransit;
using MovieTicket.Message.MovieNotification;

namespace Notification.Api.Consumers;

public class MovieNotificationCreateFailConsumer : IConsumer<MovieNotificationCreateFail>
{
    public Task Consume(ConsumeContext<MovieNotificationCreateFail> context)
    {
        throw new NotImplementedException();
    }
}