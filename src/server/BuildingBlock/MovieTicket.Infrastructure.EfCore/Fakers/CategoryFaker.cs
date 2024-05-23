using Bogus;
using MovieTicket.Domain.Entities;

namespace MovieTicket.Infrastructure.EfCore.Fakers;

public sealed class CategoryFaker : Faker<Category>
{
    public CategoryFaker(string locale = "en_US") : base(locale)
    {
        RuleFor(b => b.Id, f => f.Random.Guid());
        RuleFor(b => b.Name, f => f.Music.Genre());
    }
}