using Microsoft.AspNetCore.Identity;

namespace MovieTicketClient.IdentityServer.Data;

public class User : IdentityUser<Guid>
{
    public string FullName { get; set; }
    public string Avatar { get; set; }  
    public DateTime? Birthday { get; set; }
    public UserGender? UserGender { get; set; }
}