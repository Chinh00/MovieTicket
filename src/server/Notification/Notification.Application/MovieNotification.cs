using MassTransit;
using MediatR;
using Microsoft.EntityFrameworkCore;
using MovieTicket.Core.Domain;
using MovieTicket.Message.MovieNotification;
using Notification.Infrastructure.Data.Entities;
using Notification.Infrastructure.Firebase;
using AppContext = Notification.Infrastructure.Data.AppContext;

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

public struct RegisterDeviceTokenModel
{
    public string DeviceId { get; set; }
    public string? Token { get; set; }
    public Guid? UserId { get; set; }
}
public class RegisterDeviceToken
{
    public record Command : ICreateCommand<RegisterDeviceTokenModel, string>
    {
        public RegisterDeviceTokenModel CreateModel { get; init; }
    }
}

public class MovieNotification : 
    IRequestHandler<CreateMovieNotification.Command, ResultModel<string>>, 
    INotificationHandler<MovieNotificationCreateSuccess>, 
    IRequestHandler<RegisterDeviceToken.Command, ResultModel<string>>
{
    private readonly ITopicProducer<MovieNotificationCreate> _topicProducer;
    private readonly AppContext _appContext;
    private readonly IFirebaseNotificationService _notificationService;
    public MovieNotification(ITopicProducer<MovieNotificationCreate> topicProducer, AppContext appContext, IFirebaseNotificationService notificationService)
    {
        _topicProducer = topicProducer;
        _appContext = appContext;
        _notificationService = notificationService;
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
        var listDevice = await _appContext.DeviceTokens.ToListAsync(cancellationToken: cancellationToken);
        await _notificationService.PushNotificationDeviceAsync(listDevice.Select(e => e.Token).ToList(), "",
            cancellationToken);
    }

    public async Task<ResultModel<string>> Handle(RegisterDeviceToken.Command request, CancellationToken cancellationToken)
    {
        var deviceToken = await _appContext.DeviceTokens.AddAsync(new DeviceToken()
        {
            DeviceId = request.CreateModel.DeviceId,
            Token = request.CreateModel.Token,
        }, cancellationToken);
        await _appContext.SaveChangesAsync(cancellationToken);
        return ResultModel<string>.Create("");
    }
}