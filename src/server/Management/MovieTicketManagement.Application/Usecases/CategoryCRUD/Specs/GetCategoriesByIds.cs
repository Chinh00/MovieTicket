using System.Linq.Expressions;
using MovieTicket.Core.Specification;
using MovieTicket.Domain.Entities;

namespace MovieTicketManagement.Application.Usecases.CategoryCRUD.Specs;

public class GetCategoriesByIds : SpecificationBase<Category>
{
    private List<Guid> _guids { get; } = new List<Guid>(); 
    public GetCategoriesByIds(ICollection<Guid> categoriesId)
    {
        _guids.AddRange(categoriesId);
    }

    public override Expression<Func<Category, bool>> Criteria
    {
        get
        {
            return e => _guids.Contains(e.Id);
        }
    }
}