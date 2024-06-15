using AutoMapper;
using MediatR;
using MovieTicket.Core.Domain;
using MovieTicket.Core.Repository;
using MovieTicket.Domain.Entities;
using MovieTicket.Infrastructure.Files;
using MovieTicketManagement.Application.Usecases.CategoryCRUD;
using MovieTicketManagement.Application.Usecases.CategoryCRUD.Specs;
using MovieTicketManagement.Application.Usecases.MovieCRUD.Specs;
using Serilog;

namespace MovieTicketManagement.Application.Usecases.MovieCRUD;



public class MovieCreateModel
{
    public Guid? Id { get; init; }
    public string Name { get; init; }
    public DateTime ReleaseDate { get; init; }
    public long TotalTime { get; init; }
    public string Description { get; init; }
    public IFormFile Avatar { get; init; }
    public IFormFile Trailer { get; init; }
    public ICollection<Guid> CategoryIds { get; init; }
}

public class MovieUpdateModel
{
    public Guid Id { get; init; }
    public string Name { get; init; }
    public DateTime ReleaseDate { get; init; }
    public long TotalTime { get; init; }
    public string Description { get; init; }
    public IFormFile? Avatar { get; init; }
    public IFormFile? Trailer { get; init; }
    public ICollection<Guid> CategoryIds { get; init; }
}


public class MovieCreate
{
    public class Command : ICreateCommand<MovieCreateModel, MovieDto>
    {
        public MovieCreateModel CreateModel { get; init; }
    }
}
public class GetMovies
{
    public record Query : IListQuery<ListResultModel<MovieDto>>
    {
        public List<FilterModel> Filters { get; init; } = [];
        public List<string> Includes { get; init; } = ["Categories"];
        public List<string> SortBy { get; init; } = ["Id"];
        public int Page { get; init; } = 1;
        public int PageSize { get; init; } = 20;
    }
}
public class GetMovieById
{
    public record Query : IItemQuery<Guid, MovieDto>
    {
        public Guid Id { get; init; }
    }
}

public class UpdateMovie
{
    public record Command : IUpdateCommand<MovieUpdateModel, MovieDto>
    {
        public MovieUpdateModel CreateModel { get; init; }
    }
}



public class MovieCrud : IRequestHandler<MovieCreate.Command, ResultModel<MovieDto>>, IRequestHandler<GetMovies.Query, ResultModel<ListResultModel<MovieDto>>>, IRequestHandler<GetMovieById.Query, ResultModel<MovieDto>>, IRequestHandler<UpdateMovie.Command, ResultModel<MovieDto>>
{
    private readonly IGridRepository<Movie> _gridRepository;
    private readonly IMapper _mapper;
    private readonly IRepository<Movie> _repository;
    private readonly IFileHelper _fileHelper;
    private readonly ILogger<MovieCrud> _logger;
    private readonly IRepository<Category> _repositoryCategory;
    public MovieCrud(IGridRepository<Movie> gridRepository, IMapper mapper, IRepository<Movie> repository, IFileHelper fileHelper, ILogger<MovieCrud> logger, IRepository<Category> repositoryCategory)
    {
        _gridRepository = gridRepository;
        _mapper = mapper;
        _repository = repository;
        _fileHelper = fileHelper;
        _logger = logger;
        _repositoryCategory = repositoryCategory;
    }

    
    public async Task<ResultModel<MovieDto>> Handle(MovieCreate.Command request, CancellationToken cancellationToken)
    {
        var categories = await _repositoryCategory.FindAsync(new GetCategoriesByIds(request.CreateModel.CategoryIds));
        var movie = new Movie()
        {
            Name = request.CreateModel.Name,
            ReleaseDate = request.CreateModel.ReleaseDate,
            TotalTime = request.CreateModel.TotalTime,
            Description = request.CreateModel.Description
        };
        movie.Categories = new List<Category>();
        foreach (var category in categories)
        {
            movie.Categories.Add(category);
        }
        await _repository.AddAsync(movie);
        
        var avatarFilePath = await _fileHelper.SaveFile(request.CreateModel.Avatar, movie.Id.ToString());
        var trailerFilePath = await _fileHelper.SaveFile(request.CreateModel.Trailer, movie.Id.ToString());
        movie.Avatar = avatarFilePath;
        movie.Trailer = trailerFilePath;
        await _repository.EditAsync(movie, cancellationToken: cancellationToken);
        return ResultModel<MovieDto>.Create(_mapper.Map<MovieDto>(movie));

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

    public async Task<ResultModel<MovieDto>> Handle(GetMovieById.Query request, CancellationToken cancellationToken)
    { 
        var spec = new GetMovieByIdSpec(request.Id);
        var movie = await _repository.FindOneAsync(spec);
        return ResultModel<MovieDto>.Create(_mapper.Map<MovieDto>(movie));
    }

    public async Task<ResultModel<MovieDto>> Handle(UpdateMovie.Command request, CancellationToken cancellationToken)
    {
        ArgumentException.ThrowIfNullOrEmpty(request.CreateModel.Id.ToString());
        var movie = await _repository.FindOneAsync(
            new GetMovieByIdSpec(Guid.Parse(request.CreateModel.Id.ToString())));
        var avatarFilePath = string.Empty;
        var trailerFilePath = string.Empty;

        if (request.CreateModel.Avatar is not null)
        {
            avatarFilePath = await _fileHelper.SaveFile(request.CreateModel.Avatar, movie.Id.ToString());
        }
        if (request.CreateModel.Trailer is not null)
        {
            trailerFilePath = await _fileHelper.SaveFile(request.CreateModel.Trailer, movie.Id.ToString());
        }
        
       
        movie.Categories = new List<Category>();
        var categories = await _repositoryCategory.FindAsync(new GetCategoriesByIds(request.CreateModel.CategoryIds));
        foreach (var category in categories)
        {
            movie.Categories.Add(category);
        }
        movie.Name = request.CreateModel.Name;
        movie.ReleaseDate = request.CreateModel.ReleaseDate;
        movie.TotalTime = request.CreateModel.TotalTime;
        movie.Description = request.CreateModel.Description;
        movie.Avatar = !string.IsNullOrEmpty(avatarFilePath) ? avatarFilePath : movie.Avatar;
        movie.Trailer = !string.IsNullOrEmpty(trailerFilePath) ? trailerFilePath : movie.Trailer;

        await _repository.EditAsync(movie, cancellationToken: cancellationToken);
        return ResultModel<MovieDto>.Create(_mapper.Map<MovieDto>(movie));
    }
}

public class MovieMapperConfig : Profile
{
    public MovieMapperConfig()
    {
        CreateMap<Movie, MovieDto>();
    }
}

