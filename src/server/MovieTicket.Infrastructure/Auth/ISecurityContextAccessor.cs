namespace MovieTicket.Infrastructure.Auth;

public interface ISecurityContextAccessor
{
    object GetUserId();
}