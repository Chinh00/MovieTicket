using Duende.IdentityServer;
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

            },
            new Client
            {
                ClientId = "your_client_id", // Đặt ClientId của bạn ở đây
                ClientSecrets = { new Secret("your_client_secret".Sha256()) }, 

                AllowedGrantTypes = GrantTypes.Implicit,

                // Thêm các phạm vi (scopes) mà client có thể yêu cầu
                AllowedScopes = { 
                    IdentityServerConstants.StandardScopes.OpenId,
                    IdentityServerConstants.StandardScopes.Profile,
                    "api"
                },

                // Cấu hình các URI cho phép lấy mã ủy quyền và lấy token
                RedirectUris = { "https://your_redirect_uri/callback" }, // Đặt RedirectUri của bạn ở đây
                PostLogoutRedirectUris = { "https://your_post_logout_redirect_uri" }, // Đặt PostLogoutRedirectUri của bạn ở đây

                // Cho phép lấy thông tin về người dùng từ Google
                AllowedIdentityTokenSigningAlgorithms = { "RS256" },
                AllowOfflineAccess = true
            }
        };
    
}
