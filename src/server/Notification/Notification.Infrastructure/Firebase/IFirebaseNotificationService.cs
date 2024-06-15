namespace Notification.Infrastructure.Firebase;

public interface IFirebaseNotificationService
{
    public Task PushNotificationDeviceAsync(ICollection<string> deviceTokens, string title, string message, CancellationToken cancellationToken );
}