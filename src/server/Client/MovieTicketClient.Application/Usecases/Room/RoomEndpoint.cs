using AutoMapper;
using MediatR;
using MovieTicket.Core.Domain;
using MovieTicket.Core.Repository;
using MovieTicket.Domain.Entities;
using MovieTicketClient.Application.Usecases.Room.Specs;

namespace MovieTicketClient.Application.Usecases.Room;

public class GetRooms
{
    public class Query : IListQuery<ListResultModel<RoomDto>>
    {
        public List<FilterModel> Filters { get; init; } = [];
        public List<string> Includes { get; init; } = [];
        public List<string> SortBy { get; init; } = ["Id"];
        public int Page { get; init; } = 1;
        public int PageSize { get; init; } = 20;
    }
}

public class GetRoomById
{
    public struct Query : IItemQuery<Guid, RoomDto>
    {
        public Guid Id { get; init; }
    }
}



public class RoomEndpoint : IRequestHandler<GetRooms.Query, ResultModel<ListResultModel<RoomDto>>>, IRequestHandler<GetRoomById.Query, ResultModel<RoomDto>>
{
    private readonly IRepository<MovieTicket.Domain.Entities.Room> _repository;
    private readonly IGridRepository<MovieTicket.Domain.Entities.Room> _gridRepository;
    private readonly IMapper _mapper;
    public RoomEndpoint(IRepository<MovieTicket.Domain.Entities.Room> repository, IMapper mapper, IGridRepository<MovieTicket.Domain.Entities.Room> gridRepository)
    {
        _repository = repository;
        _mapper = mapper;
        _gridRepository = gridRepository;
    }

    public async Task<ResultModel<ListResultModel<RoomDto>>> Handle(GetRooms.Query request, CancellationToken cancellationToken)
    {
        var spec = new GetRoomsSpec<RoomDto>(request);
        var rooms = await _gridRepository.FindAsync(spec);
        var totalRooms = await _gridRepository.CountAsync(spec);
        var listResultModel = ListResultModel<RoomDto>.Create(_mapper.Map<List<RoomDto>>(rooms), totalRooms, request.Page, request.PageSize);
        return ResultModel<ListResultModel<RoomDto>>.Create(listResultModel);
    }

    public async Task<ResultModel<RoomDto>> Handle(GetRoomById.Query request, CancellationToken cancellationToken)
    {
        var spec = new GetRoomByIdSpec(request.Id);
        var room = await _repository.FindOneAsync(spec);
        return ResultModel<RoomDto>.Create(_mapper.Map<RoomDto>(room));
    }
}

public class RoomMapperConfig : Profile
{
    public RoomMapperConfig()
    {
        CreateMap<MovieTicket.Domain.Entities.Room, RoomDto>();
        CreateMap<Seat, SeatDto>();
    }
}