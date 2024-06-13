using MovieTicket.Core.Domain;

namespace Notification.Infrastructure.Data.Entities;

public class DeviceToken : EntityBase
{
    public string DeviceId { get; set; }
    public string? Token { get; set; }
    public Guid? UserId { get; set; }
}