using AutoMapper;
using MediatR;
using MovieTicket.Core.Domain;
using MovieTicket.Core.Repository;
using MovieTicket.Domain.Entities;
using MovieTicket.Infrastructure.Files;
using MovieTicketManagement.Application.Usecases.ServiceCRUD.Specs;

namespace MovieTicketManagement.Application.Usecases.ServiceCRUD;

public class ServiceCreateModel
{
    public String Name { get; set; }
    public String Unit { get; set; }
    public Double PriceUnit { get; set; }
    public IFormFile? Avatar { get; set; }
}


public class CreateService
{
    public record Command : ICreateCommand<ServiceCreateModel, ServiceDto>
    {
        public ServiceCreateModel CreateModel { get; init; }
    }
}

public class GetServices {
    public record Query : IListQuery<ListResultModel<ServiceDto>>
    {
        public List<FilterModel> Filters { get; init; } = [];
        public List<string> Includes { get; init; } = [];
        public List<string> SortBy { get; init; } = [];
        public int Page { get; init; } = 1;
        public int PageSize { get; init; } = 20;
    }
}

public class ServiceCrud : IRequestHandler<GetServices.Query, ResultModel<ListResultModel<ServiceDto>>>, IRequestHandler<CreateService.Command, ResultModel<ServiceDto>>
{
    private readonly IGridRepository<Service> _gridRepository;
    private readonly IRepository<Service> _repository;
    private readonly IMapper _mapper;
    private readonly IFileHelper _fileHelper;
    public ServiceCrud(IGridRepository<Service> gridRepository, IMapper mapper, IRepository<Service> repository, IFileHelper fileHelper)
    {
        _gridRepository = gridRepository;
        _mapper = mapper;
        _repository = repository;
        _fileHelper = fileHelper;
    }

    public async Task<ResultModel<ListResultModel<ServiceDto>>> Handle(GetServices.Query request, CancellationToken cancellationToken)
    {
        var spec = new GetServicesSpec<ServiceDto>(request);
        var services = await _gridRepository.FindAsync(spec);
        var totalServices = await _gridRepository.CountAsync(spec);
        var listResultModel = ListResultModel<ServiceDto>.Create(_mapper.Map<List<ServiceDto>>(services),
            totalServices, request.Page, request.PageSize);
        return ResultModel<ListResultModel<ServiceDto>>.Create(listResultModel);        
    }


    public async Task<ResultModel<ServiceDto>> Handle(CreateService.Command request, CancellationToken cancellationToken)
    {
        var image = await _fileHelper.SaveFile(request.CreateModel.Avatar, BaseDirectory.Service);
        var service = await _repository.AddAsync(new Service()
        {
            Name = request.CreateModel.Name,
            Avatar = image,
            Unit = request.CreateModel.Unit,
            PriceUnit = request.CreateModel.PriceUnit
        });
        await _repository.AddAsync(service);
        return ResultModel<ServiceDto>.Create(_mapper.Map<ServiceDto>(service));
    }
}

class ServiceMapperConfig : Profile
{
    public ServiceMapperConfig()
    {
        CreateMap<Service, ServiceDto>();
    }
}