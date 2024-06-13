using MediatR;

namespace MovieTicket.Message.MovieNotification;

public class MovieNotificationCreateFail : INotification
{
    public string MessageError { get; set; }
}