﻿using System.Security.Claims;
using Duende.IdentityServer.Models;
using Duende.IdentityServer.Validation;
using Google.Apis.Auth;
using Microsoft.AspNetCore.Identity;
using MovieTicketClient.IdentityServer.Data;

namespace MovieTicketClient.IdentityServer;

public class ExternalGrantValidator : IExtensionGrantValidator
{
    private readonly UserManager<User> _userManager;

    public ExternalGrantValidator(UserManager<User> userManager)
    {
        _userManager = userManager;
    }

    public async Task ValidateAsync(ExtensionGrantValidationContext context)
    {
        var provider = context.Request.Raw["provider"];
        var token = context.Request.Raw["token"];

        if (string.IsNullOrWhiteSpace(provider) || string.IsNullOrWhiteSpace(token))
        {
            context.Result = new GrantValidationResult(TokenRequestErrors.InvalidGrant, "Invalid token");
            return;
        }

        var payload = await GoogleJsonWebSignature.ValidateAsync(token);
        if (payload == null)
        {
            context.Result = new GrantValidationResult(TokenRequestErrors.InvalidGrant, "Invalid token");
            return;
        }

        var user = await _userManager.FindByEmailAsync(payload.Email);
        if (user == null)
        {
            user = new User() { UserName = payload.Email, Email = payload.Email };
            var result = await _userManager.CreateAsync(user);
            if (!result.Succeeded)
            {
                context.Result = new GrantValidationResult(TokenRequestErrors.InvalidGrant, "User creation failed");
                return;
            }
        }

        var claims = new List<Claim>
        {
            new Claim(ClaimTypes.NameIdentifier, user.Id.ToString()),
            new Claim(ClaimTypes.Email, user.Email.ToString())
        };

        context.Result = new GrantValidationResult(
            subject: user.Id.ToString(),
            authenticationMethod: provider,
            claims: claims,
            identityProvider: provider,
            customResponse: new Dictionary<string, object>()
        );
    }

    public string GrantType => "external";
}