using MediatR;
using MovieTicket.Domain.Entities;

namespace MovieTicket.Message.MovieNotification;

public class MovieNotificationCreateSuccess : INotification
{
    public Movie Movie { get; set; }
    public string Message { get; set; }
    public DateTime? SendTime { get; set; }
}