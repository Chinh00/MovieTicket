namespace Notification.Extensions;

public interface INotification
{
    
}

public interface IDeviceNotification
{
    public Task PushAsync(List<string> deviceTokens, IMessageBody messageBody, CancellationToken cancellationToken);
}



public interface IMessageBody
{
    
}