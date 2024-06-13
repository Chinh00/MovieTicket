using FirebaseAdmin;
using Google.Apis.Auth.OAuth2;

namespace Notification.Infrastructure.Firebase;

public class FirebaseNotificationService : IFirebaseNotificationService
{
    public FirebaseNotificationService()
    {
        FirebaseApp.Create(new AppOptions()
        {
            Credential = GoogleCredential.FromFile("../../Notification/Notification.Api/android-5e2d0-firebase-adminsdk-p2ikp-adbe0b9280.json")
        });
    }
    public Task PushNotificationDeviceAsync(ICollection<string> deviceTokens, string message, CancellationToken cancellationToken)
    {
        return Task.CompletedTask;
    }
}