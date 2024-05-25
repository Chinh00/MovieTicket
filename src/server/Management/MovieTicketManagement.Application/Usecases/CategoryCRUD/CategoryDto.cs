using AutoMapper;
using MovieTicket.Domain.Entities;

namespace MovieTicketManagement.Application.Usecases.CategoryCRUD;

public class CategoryDto
{
    public Guid Id { get; init; } = Guid.NewGuid();
    public DateTime CreatedDate { get; init; }
    public DateTime? UpdatedDate { get; init; }
    public string Name { get; init; }
}

public class CategoryMapperConfig : Profile
{
    public CategoryMapperConfig()
    {
        CreateMap<Category, CategoryDto>();
    }
}