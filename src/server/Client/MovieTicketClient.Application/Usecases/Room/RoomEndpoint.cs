using AutoMapper;
using MediatR;
using MovieTicket.Core.Domain;
using MovieTicket.Core.Repository;
using MovieTicket.Domain.Entities;
using MovieTicketClient.Application.Usecases.Reservation.Specs;
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

class GetRoomByIdModel
{
    public Guid RoomId { get; set; }
    public Guid ScreeningId { get; set; }
}

public class RoomDtoState
{
    public Guid Id { get; set; }
    public DateTime CreatedDate { get; set; }
    public DateTime? UpdatedDate { get; set; }
    
    public int RoomNumber { get; set; }
    
    public ICollection<SeatDtoState> Seats { get; set; }
}

public class SeatDtoState
{
    public Guid Id { get; init; }
    public string RowNumber { get; init; }
    public string ColNumber { get; init; }
    public bool IsPlaced { get; set; }
}

public class QueryRoom : IRequest<ResultModel<RoomDtoState>>
{
    public Guid RoomId { get; set; }
    public Guid ScreeningId { get; set; }
}

public class RoomEndpoint : IRequestHandler<GetRooms.Query, ResultModel<ListResultModel<RoomDto>>>, IRequestHandler<QueryRoom, ResultModel<RoomDtoState>>
{
    private readonly IRepository<MovieTicket.Domain.Entities.Room> _repository;
    private readonly IGridRepository<MovieTicket.Domain.Entities.Room> _gridRepository;
    private readonly IRepository<MovieTicket.Domain.Entities.Reservation> _repositoryReservation;
    private readonly IMapper _mapper;
    public RoomEndpoint(IRepository<MovieTicket.Domain.Entities.Room> repository, IMapper mapper, IGridRepository<MovieTicket.Domain.Entities.Room> gridRepository, IRepository<MovieTicket.Domain.Entities.Reservation> repositoryReservation)
    {
        _repository = repository;
        _mapper = mapper;
        _gridRepository = gridRepository;
        _repositoryReservation = repositoryReservation;
    }

    public async Task<ResultModel<ListResultModel<RoomDto>>> Handle(GetRooms.Query request, CancellationToken cancellationToken)
    {
        var spec = new GetRoomsSpec<RoomDto>(request);
        var rooms = await _gridRepository.FindAsync(spec);
        var totalRooms = await _gridRepository.CountAsync(spec);
        var listResultModel = ListResultModel<RoomDto>.Create(_mapper.Map<List<RoomDto>>(rooms), totalRooms, request.Page, request.PageSize);
        return ResultModel<ListResultModel<RoomDto>>.Create(listResultModel);
    }


    public async Task<ResultModel<RoomDtoState>> Handle(QueryRoom request, CancellationToken cancellationToken)
    {

        var reservation =
            await _repositoryReservation.FindAsync(new GetReservationByScreeningIdSpec(request.ScreeningId));
        var room = await _repository.FindOneAsync(new GetRoomByIdSpec(request.RoomId));
        
        
        var list_res = new RoomDtoState();
        list_res.Id = room.Id;
        list_res.RoomNumber = room.RoomNumber;
        list_res.Seats = new List<SeatDtoState>();
        foreach (var roomSeat in room.Seats)
        {
            list_res.Seats.Add(new SeatDtoState()
            {
                Id = roomSeat.Id,
                RowNumber = roomSeat.RowNumber,
                ColNumber = roomSeat.ColNumber,
                IsPlaced = reservation.Any(e => e.SeatReservations.Any(t => t.SeatId == roomSeat.Id))
            });
        }
        return ResultModel<RoomDtoState>.Create(list_res);
    }
}

public class RoomMapperConfig : Profile
{
    public RoomMapperConfig()
    {
        CreateMap<MovieTicket.Domain.Entities.Room, RoomDto>();
        CreateMap<MovieTicket.Domain.Entities.Room, RoomDtoState>();
        CreateMap<Seat, SeatDto>();
        CreateMap<Seat, SeatDtoState>();
    }
}