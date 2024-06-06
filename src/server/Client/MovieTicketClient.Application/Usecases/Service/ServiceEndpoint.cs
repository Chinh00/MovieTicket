using AutoMapper;
using MediatR;
using MovieTicket.Core.Domain;
using MovieTicket.Core.Repository;
using MovieTicketClient.Application.Usecases.Service.Specs;

namespace MovieTicketClient.Application.Usecases.Service;

public class GetServices
{
    public record Query : IListQuery<ListResultModel<ServiceDto>>
    {
        public List<FilterModel> Filters { get; init; } = [];
        public List<string> Includes { get; init; } = [];
        public List<string> SortBy { get; init; } = [];
        public int Page { get; init; } = 1;
        public int PageSize { get; init; } = 20;
    }
}

public class ServiceEndpoint : IRequestHandler<GetServices.Query, ResultModel<ListResultModel<ServiceDto>>>
{
    private readonly IGridRepository<MovieTicket.Domain.Entities.Service> _gridRepository;
    private readonly IMapper _mapper;

    public ServiceEndpoint(IGridRepository<MovieTicket.Domain.Entities.Service> gridRepository, IMapper mapper)
    {
        _gridRepository = gridRepository;
        _mapper = mapper;
    }

    public async Task<ResultModel<ListResultModel<ServiceDto>>> Handle(GetServices.Query request, CancellationToken cancellationToken)
    {
        var spec = new GetServicesSpec<ServiceDto>(request);
        var services = await _gridRepository.FindAsync(spec);
        var serviceTotals = await _gridRepository.CountAsync(spec);
        var listResultModel = ListResultModel<ServiceDto>.Create(_mapper.Map<List<ServiceDto>>(services), serviceTotals,
            request.Page, request.PageSize);
        
        return ResultModel<ListResultModel<ServiceDto>>.Create(listResultModel);
    }
}

public class ServiceMapperConfig : Profile
{
    public ServiceMapperConfig()
    {
        CreateMap<MovieTicket.Domain.Entities.Service, ServiceDto>();
    }
}