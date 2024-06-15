using MediatR;

namespace MovieTicket.Message.MovieNotification;

public record MovieNotificationCreate : INotification
{
    public Guid MovieId { get; set; }
    public string Message { get; set; }
    public DateTime? SendTime { get; set; }
}