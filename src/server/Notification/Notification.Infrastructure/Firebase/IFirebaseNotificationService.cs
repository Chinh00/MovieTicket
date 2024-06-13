namespace Notification.Infrastructure.Firebase;

public interface IFirebaseNotificationService
{
    public Task PushNotificationDeviceAsync(ICollection<string> deviceTokens, string message, CancellationToken cancellationToken );
}