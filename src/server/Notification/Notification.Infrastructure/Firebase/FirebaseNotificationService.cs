using FirebaseAdmin;
using Google.Apis.Auth.OAuth2;

namespace Notification.Infrastructure.Firebase;

public class FirebaseNotificationService : IFirebaseNotificationService
{
    public FirebaseNotificationService()
    {
        FirebaseApp.Create(new AppOptions()
        {
            Credential = GoogleCredential.FromJson("../../Notification/Notification.Api/google-services.json")
        });
    }
    public Task PushNotificationDeviceAsync(ICollection<string> deviceTokens, string message, CancellationToken cancellationToken)
    {
        return Task.CompletedTask;
    }
}