using MassTransit;
using MediatR;
using MovieTicket.Core.Domain;
using MovieTicket.Message.MovieNotification;

namespace Notification.Application;

public struct MovieNotificationCreateModel
{
    public Guid MovieId { get; set; }
    public string Message { get; set; }
    public DateTime? SendTime { get; set; }
}

public class CreateMovieNotification
{
    public record Command : ICreateCommand<MovieNotificationCreateModel, string>
    {
        public MovieNotificationCreateModel CreateModel { get; init; }
    }
}


public class MovieNotification : IRequestHandler<CreateMovieNotification.Command, ResultModel<string>>, INotificationHandler<MovieNotificationCreateSuccess>
{
    private readonly IPublishEndpoint _publishEndpoint;
    private readonly ITopicProducer<MovieNotificationCreate> _topicProducer;
    public MovieNotification(IPublishEndpoint publishEndpoint, ITopicProducer<MovieNotificationCreate> topicProducer)
    {
        _publishEndpoint = publishEndpoint;
        _topicProducer = topicProducer;
    }

    public async Task<ResultModel<string>> Handle(CreateMovieNotification.Command request, CancellationToken cancellationToken)
    {
        await _topicProducer.Produce(new MovieNotificationCreate()
        {
            MovieId = request.CreateModel.MovieId
        }, cancellationToken);
        return ResultModel<string>.Create("");
    }

    public async Task Handle(MovieNotificationCreateSuccess notification, CancellationToken cancellationToken)
    {
        throw new NotImplementedException();
    }
}