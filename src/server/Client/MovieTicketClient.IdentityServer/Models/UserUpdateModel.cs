using MovieTicketClient.IdentityServer.Data;

namespace MovieTicketClient.IdentityServer.Models;

public class UserUpdateModel
{
    public string? FullName { get; set; }
    public DateTime? Birthday { get; set; }
    public IFormFile? Avatar { get; set; }
    public UserGender? UserGender { get; set; }
}