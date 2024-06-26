using MovieTicketClient.IdentityServer.Data;

namespace MovieTicketClient.IdentityServer.Models;

public class UserUpdateModel
{
    public string? FullName { get; set; }
    public DateTime? Birthday { get; set; }
    public string Avatar { get; set; }
    public UserGender? UserGender { get; set; }
}