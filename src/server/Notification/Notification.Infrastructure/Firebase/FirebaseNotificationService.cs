using FirebaseAdmin;
using FirebaseAdmin.Messaging;
using Google.Apis.Auth.OAuth2;

namespace Notification.Infrastructure.Firebase;

public class FirebaseNotificationService : IFirebaseNotificationService
{
    public FirebaseNotificationService()
    {
    }
    public async Task PushNotificationDeviceAsync(ICollection<string> deviceTokens, string title, string message, CancellationToken cancellationToken)
    {
        var multicastMessage = new MulticastMessage()
        {
            Tokens = new List<string>(deviceTokens),
            Notification = new FirebaseAdmin.Messaging.Notification()
            {
                Title = title,
                Body = message,
            }
        };
        var response = await FirebaseMessaging.DefaultInstance.SendMulticastAsync(multicastMessage, cancellationToken);
    }
}