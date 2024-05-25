using AutoMapper;
using MediatR;
using MovieTicket.Core.Domain;
using MovieTicket.Core.Repository;
using MovieTicket.Domain.Entities;
using MovieTicketManagement.Application.Usecases.CategoryCRUD.Specs;

namespace MovieTicketManagement.Application.Usecases.CategoryCRUD;


public class GetCategories
{
    public record Query : IListQuery<ListResultModel<CategoryDto>>
    {
        public List<FilterModel> Filters { get; init; } = [];
        public List<string> Includes { get; init; } = [];
        public List<string> SortBy { get; init; } = [];
        public int Page { get; init; } = 1;
        public int PageSize { get; init; } = 20;
    }
}

public class CategoryCrud : IRequestHandler<GetCategories.Query, ResultModel<ListResultModel<CategoryDto>>>
{
    private readonly IGridRepository<Category> _gridRepository;
    private readonly IMapper _mapper;

    public CategoryCrud(IGridRepository<Category> gridRepository, IMapper mapper)
    {
        _gridRepository = gridRepository;
        _mapper = mapper;
    }

    public async Task<ResultModel<ListResultModel<CategoryDto>>> Handle(GetCategories.Query request, CancellationToken cancellationToken)
    {
        var spec = new GetCategories<CategoryDto>(request);
        var categories = await _gridRepository.FindAsync(spec);
        var movieTotals = await _gridRepository.CountAsync(spec);
        var listResultModel = ListResultModel<CategoryDto>.Create(_mapper.Map<List<CategoryDto>>(categories), movieTotals,
            request.Page, request.PageSize);
        
        return ResultModel<ListResultModel<CategoryDto>>.Create(listResultModel);
    }
}