using System.IdentityModel.Tokens.Jwt;
using System.Security.Claims;
using Duende.IdentityServer.Models;
using Duende.IdentityServer.Validation;
using FirebaseAdmin.Auth;
using Microsoft.EntityFrameworkCore;
using Microsoft.IdentityModel.Tokens;
using MovieTicketClient.IdentityServer.Data;

namespace MovieTicketClient.IdentityServer;

public class FirebaseGrantValidator : IExtensionGrantValidator
{
    private readonly UserManager _userManager;
    private readonly AppDbContext _dbContext;

    public FirebaseGrantValidator(UserManager userManager, AppDbContext dbContext)
    {
        _userManager = userManager;
        _dbContext = dbContext;
    }

    public async Task ValidateAsync(ExtensionGrantValidationContext context)
    {
        var idToken = context.Request.Raw.Get("token");
        var provider = context.Request.Raw.Get("provider");
       
        
        if (string.IsNullOrWhiteSpace(provider) || string.IsNullOrWhiteSpace(idToken))
        {
            context.Result = new GrantValidationResult(TokenRequestErrors.InvalidGrant, "Invalid token");
            return;
        }
        
        var decodedToken = await FirebaseAuth.DefaultInstance.VerifyIdTokenAsync(idToken);
        
        if (decodedToken == null )
        {
            context.Result = new GrantValidationResult(TokenRequestErrors.InvalidGrant, "Invalid token");
            return;
        }
        var phone = decodedToken.Claims["phone_number"];

        var user = await _dbContext.Set<User>().Where(e => e.PhoneNumber == phone).FirstOrDefaultAsync();
        if (user == null)
        {
            user = new User() { UserName = phone.ToString(), PhoneNumber = phone.ToString() };
            var result = await _userManager.CreateAsync(user);
            if (!result.Succeeded)
            {
                context.Result = new GrantValidationResult(TokenRequestErrors.InvalidGrant, "User creation failed");
                return;
            }
        }
        await _userManager.UpdateSecurityStampAsync(user);
        var claims = new List<Claim>
        {
            new Claim(ClaimTypes.NameIdentifier, user.Id.ToString()),
            new Claim(ClaimTypes.MobilePhone, user.PhoneNumber)
        };

        context.Result = new GrantValidationResult(
            subject: user.Id.ToString(),
            authenticationMethod: provider,
            claims: claims,
            identityProvider: provider,
            customResponse: new Dictionary<string, object>()
        );
        
        
        
    }

    public string GrantType => "firebase_token";
}