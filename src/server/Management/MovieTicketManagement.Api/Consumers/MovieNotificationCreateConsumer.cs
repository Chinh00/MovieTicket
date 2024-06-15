using MassTransit;
using MovieTicket.Core.Repository;
using MovieTicket.Domain.Entities;
using MovieTicket.Message.MovieNotification;

namespace MovieTicketManagement.Api.Consumers;

public class MovieNotificationCreateConsumer : IConsumer<MovieNotificationCreate>
{
    private ITopicProducer<MovieNotificationCreateSuccess> _topicProducerSuccess;
    private ITopicProducer<MovieNotificationCreateFail> _topicProducerFail;
    private readonly IRepository<Movie> _repository;

    public MovieNotificationCreateConsumer(IPublishEndpoint publishEndpoint, IRepository<Movie> repository, ITopicProducer<MovieNotificationCreateSuccess> topicProducerSuccess, ITopicProducer<MovieNotificationCreateFail> topicProducerFail)
    {
        _repository = repository;
        _topicProducerSuccess = topicProducerSuccess;
        _topicProducerFail = topicProducerFail;
    }

    public async Task Consume(ConsumeContext<MovieNotificationCreate> context)
    {
        var movie = _repository.FindById(context.Message.MovieId);
        await _topicProducerSuccess.Produce(new MovieNotificationCreateSuccess()
        {
            Movie = movie,
            Message = context.Message.Message,
            SendTime = context.Message.SendTime
        });
    }
}