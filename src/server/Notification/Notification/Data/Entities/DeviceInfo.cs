namespace Notification.Data.Entities;

public class DeviceInfo
{
    public Guid Id { get; set; }
    public Guid? UserId { get; set; }
    public string DeviceToken { get; set; }
    public DateTime CreatedDate { get; set; }
    public DateTime? UpdatedDate { get; set; }
}