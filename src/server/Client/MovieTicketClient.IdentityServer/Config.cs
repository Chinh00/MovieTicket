using Duende.IdentityServer.Models;

namespace MovieTicketClient.IdentityServer;

public static class Config
{
    
    public static IEnumerable<IdentityResource> IdentityResources =>
        new IdentityResource[]
        {
            new IdentityResources.OpenId(),
            new IdentityResources.Profile(),
        };
    public static IEnumerable<ApiScope> ApiScopes =>
        new ApiScope[]
        {
            new ApiScope("api", "Api for Mobile")
        };
    
    public static IEnumerable<Client> Clients =>
        new Client[]
        {
            new Client()
            {
                ClientId = "react_client",
                AllowedGrantTypes = GrantTypes.ResourceOwnerPassword,
                RequireClientSecret = false,
                AllowedScopes = { "openid", "profile", "api" },
                AllowOfflineAccess = true // Để sử dụng refresh tokens

            }
        };
    
}
