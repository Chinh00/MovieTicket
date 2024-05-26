using MovieTicket.Core.Specification;
using MovieTicket.Domain.Entities;

namespace MovieTicketManagement.Application.Usecases.CategoryCRUD.Specs;

public class GetCategoriesByIds : GridSpecificationBase<Category>
{
    public GetCategoriesByIds(ICollection<Guid> categoriesId)
    {
        foreach (var guid in categoriesId)
        {
            ApplyFilter(e => e.Id == guid);
        }
    }
}