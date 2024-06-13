using MediatR;

namespace MovieTicket.Message.MovieNotification;

public record MovieNotificationCreate : INotification
{
    public Guid MovieId { get; set; }
}