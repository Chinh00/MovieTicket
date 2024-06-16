using FirebaseAdmin.Messaging;
using MassTransit;
using MediatR;
using Microsoft.EntityFrameworkCore;
using MovieTicket.Core.Domain;
using MovieTicket.Message.MovieNotification;
using Notification.Infrastructure.Data;
using Notification.Infrastructure.Data.Entities;
using Notification.Infrastructure.Firebase;

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

public record RegisterDeviceTokenModel
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
    private readonly Infrastructure.Data.AppDbContext _appDbContext;
    private readonly IFirebaseNotificationService _notificationService;
    public MovieNotification(ITopicProducer<MovieNotificationCreate> topicProducer, AppDbContext appDbContext, IFirebaseNotificationService notificationService)
    {
        _topicProducer = topicProducer;
        _appDbContext = appDbContext;
        _notificationService = notificationService;
    }

    public async Task<ResultModel<string>> Handle(CreateMovieNotification.Command request, CancellationToken cancellationToken)
    {
        await _topicProducer.Produce(new MovieNotificationCreate()
        {
            MovieId = request.CreateModel.MovieId,
        }, cancellationToken);
        return ResultModel<string>.Create("");
    }

    public async Task Handle(MovieNotificationCreateSuccess notification, CancellationToken cancellationToken)
    {
        var data = new Dictionary<string, string> { { "id", notification.Movie.Id.ToString() } };
        var multicastMessage = new MulticastMessage()
        {
            Tokens = new List<string>(await _appDbContext.Set<DeviceToken>().Select(e => e.Token).AsQueryable().ToListAsync(cancellationToken: cancellationToken)),
            Notification = new FirebaseAdmin.Messaging.Notification()
            {
                Title = notification.Movie.Name,
                ImageUrl = "http://codewithme.id.vn/files/19585d7e-dd1f-4dab-ace3-fa485a0ac89a/Pee-Nak-3-1-poster.jpg",
            },
            Android = new AndroidConfig()
            {
                Priority = Priority.Normal,
                Notification = new AndroidNotification()
                {
                    ClickAction = "DETAIL_FILM_ACTIVITY"
                },
                Data = data
                
            }
        };
        await FirebaseMessaging.DefaultInstance.SendMulticastAsync(multicastMessage, cancellationToken);
        
    }

    public async Task<ResultModel<string>> Handle(RegisterDeviceToken.Command request, CancellationToken cancellationToken)
    {
        await _appDbContext.Set<DeviceToken>().AddAsync(new DeviceToken()
        {

            DeviceId = request.CreateModel.DeviceId,
            Token = request.CreateModel.Token,
        }, cancellationToken);
        
        await _appDbContext.SaveChangesAsync(cancellationToken);
        return ResultModel<string>.Create("");
    }
}