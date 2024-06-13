using FirebaseAdmin;
using FirebaseAdmin.Messaging;
using Google.Apis.Auth.OAuth2;

namespace Notification.Infrastructure.Firebase;

public class FirebaseNotificationService : IFirebaseNotificationService
{
    public FirebaseNotificationService()
    {
    }
    public Task PushNotificationDeviceAsync(ICollection<string> deviceTokens, string message, CancellationToken cancellationToken)
    {
        FirebaseMessaging.DefaultInstance.SendAsync(new Message()
        {
            Token = "c_7Whqk4TmGZby9FYfowPK:APA91bHPOzDK34eFgXpgCYHwisfqchhgk3rqOGXu9T6jD4NwJUlvmQwifkVgV5URtIO8bONK2o0CW0punKjxnLW6mv_hapb22XMPYKpoAtTYrlOrXgj37GOfczP7U1p3iNiMKRfOlTe7",
            Notification = new FirebaseAdmin.Messaging.Notification()
            {
                Title = "Sdvds",
                Body = "sdvsd",
            }
        }, cancellationToken);
        return Task.CompletedTask;
    }
}