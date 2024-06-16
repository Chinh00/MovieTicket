using FirebaseAdmin;
using FirebaseAdmin.Messaging;
using Google.Apis.Auth.OAuth2;

namespace Notification.Infrastructure.Firebase;

public class FirebaseNotificationService : IFirebaseNotificationService
{
    public FirebaseNotificationService()
    {
    }
    public async Task PushNotificationDeviceAsync(ICollection<string> deviceTokens, string title, string message, string imageUrl, CancellationToken cancellationToken)
    {
        var multicastMessage = new MulticastMessage()
        {
            Tokens = new List<string>(deviceTokens),
            Notification = new FirebaseAdmin.Messaging.Notification()
            {
                Title = title,
                Body = message,
                ImageUrl = imageUrl,
            },
            Android = new AndroidConfig()
            {
                Priority = Priority.Normal,
                Notification = new AndroidNotification()
                {
                    ClickAction = "DETAIL_FILM_ACTIVITY"
                }
            }
        };
        var response = await FirebaseMessaging.DefaultInstance.SendMulticastAsync(multicastMessage, cancellationToken);
    }
}