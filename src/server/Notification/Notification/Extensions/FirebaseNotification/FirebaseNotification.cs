using FirebaseAdmin;
using FirebaseAdmin.Messaging;
using Google.Apis.Auth.OAuth2;

namespace Notification.Extensions.FirebaseNotification;

public class FirebaseNotification : IDeviceNotification
{
    public FirebaseNotification()
    {
        FirebaseApp.Create(new AppOptions()
        {
            Credential = GoogleCredential.FromFile("path_to_your_firebase_adminsdk.json")
        });
    }

    public Task PushAsync(List<string> deviceTokens, IMessageBody messageBody, CancellationToken cancellationToken)
    {
        var message = new MulticastMessage()
        {
            Tokens = deviceTokens,
            Notification = new FirebaseAdmin.Messaging.Notification()
            {

            }
        };
        return Task.CompletedTask;
    }
}