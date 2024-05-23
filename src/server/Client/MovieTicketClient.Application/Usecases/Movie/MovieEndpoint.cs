using AutoMapper;
using MediatR;
using MovieTicket.Core.Domain;
using MovieTicket.Core.Repository;
using MovieTicket.Domain.Entities;
using MovieTicketClient.Application.Usecases.Movie.Specs;

namespace MovieTicketClient.Application.Usecases.Movie;

public record MovieCreateModel
{
    public Guid Id { get; init; }
    public string Name { get; init; }
    public DateTime ReleaseDate { get; init; }
    public long TotalTime { get; init; }
    public string Description { get; init; }
    public ICollection<Guid> CategoryId { get; } = [];
    
}

public record MovieUpdateModel
{
    
}

public class MovieCreate
{
    public record Command : ICreateCommand<MovieCreateModel, MovieDto>
    {
        public MovieCreateModel CreateModel { get; init; }

    }
}

public class MovieUpdate
{
    public record Command : IUpdateCommand<MovieCreateModel, MovieDto>
    {
        public MovieCreateModel CreateModel { get; init; }
    }
}

public class GetMovies
{
    public record Query : IListQuery<ListResultModel<MovieDto>>
    {
        public List<FilterModel> Filters { get; init; } = [];
        public List<string> Includes { get; init; } = [];
        public List<string> SortBy { get; init; } = [];
        public List<string> SortByDescending { get; init; } 
        public int Page { get; init; } = 1;
        public int PageSize { get; init; } = 20;
    }
}



public class MovieEndpoint : IRequestHandler<MovieCreate.Command, ResultModel<MovieDto>>, IRequestHandler<MovieUpdate.Command, ResultModel<MovieDto>>, IRequestHandler<GetMovies.Query, ResultModel<ListResultModel<MovieDto>>>
{
    private readonly IRepository<MovieTicket.Domain.Entities.Movie> _repository;
    private readonly IRepository<Category> _repositoryCategory;
    private readonly IGridRepository<MovieTicket.Domain.Entities.Movie> _gridRepository;
    private readonly IMapper _mapper;
    public MovieEndpoint(IRepository<MovieTicket.Domain.Entities.Movie> repository, IRepository<Category> repositoryCategory, IGridRepository<MovieTicket.Domain.Entities.Movie> gridRepository, IMapper mapper)
    {
        _repository = repository;
        _repositoryCategory = repositoryCategory;
        _gridRepository = gridRepository;
        _mapper = mapper;
    }

    public async Task<ResultModel<MovieDto>> Handle(MovieCreate.Command request, CancellationToken cancellationToken)
    {
        var movie = new MovieTicket.Domain.Entities.Movie()
        {
            Name = request.CreateModel.Name,
            ReleaseDate = request.CreateModel.ReleaseDate,
            TotalTime = request.CreateModel.TotalTime,
            Description = request.CreateModel.Description
        };
        foreach (var guid in request.CreateModel.CategoryId)
        {
            var category = _repositoryCategory.FindById(guid);
            movie.Categories.Add(category);
        }
        
        await _repository.AddAsync(movie);
        return ResultModel<MovieDto>.Create(new MovieDto());
    }

    public async Task<ResultModel<MovieDto>> Handle(MovieUpdate.Command request, CancellationToken cancellationToken)
    {
        var movie = _repository.FindById(request.CreateModel.Id);
        throw new NotImplementedException();
    }

    public async Task<ResultModel<ListResultModel<MovieDto>>> Handle(GetMovies.Query request, CancellationToken cancellationToken)
    {
        var spec = new GetMoviesSpecs<MovieDto>(request);
        var movies = await _gridRepository.FindAsync(spec);
        var movieTotals = await _gridRepository.CountAsync(spec);
        var listResultModel = ListResultModel<MovieDto>.Create(_mapper.Map<List<MovieDto>>(movies), movieTotals,
            request.Page, request.PageSize);
        
        return ResultModel<ListResultModel<MovieDto>>.Create(listResultModel);
    }
}

public class MovieMapperConfig : Profile
{
    public MovieMapperConfig()
    {
        CreateMap<MovieTicket.Domain.Entities.Movie, MovieDto>();
    }
}


