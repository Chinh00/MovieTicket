using Bogus;
using MovieTicket.Domain.Entities;

namespace MovieTicket.Infrastructure.EfCore.Fakers;

public sealed class MovieFaker : Faker<Movie>
{
    public MovieFaker(string locale = "en_US") : base(locale)
    {
        var categories = new CategoryFaker().Generate(50);
        RuleFor(b => b.Id, e => e.Random.Guid());
        RuleFor(b => b.Name, e => e.Music.Genre());
        RuleFor(b => b.ReleaseDate, e => e.Date.Past(0));
        RuleFor(b => b.TotalTime, e => e.Random.Int());
        RuleFor(b => b.Description, e => e.Lorem.Paragraph(100));
        RuleFor(b => b.Avatar, e => e.Image.PicsumUrl());
        RuleFor(b => b.Trailer, e => e.Image.PicsumUrl());
        RuleFor(b => b.Categories, f => new CategoryFaker("en_US").Generate(f.Random.Number(1, 5)));
    }
}