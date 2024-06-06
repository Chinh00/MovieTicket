using AutoMapper;
using MediatR;
using MovieTicket.Core.Domain;
using MovieTicket.Core.Repository;
using MovieTicket.Domain.Entities;
using MovieTicketManagement.Application.Usecases.RoomCRUD.Specs;

namespace MovieTicketManagement.Application.Usecases.RoomCRUD;

public class RoomCreateModel
{
    public int RoomNumber { get; set; }
    public ICollection<SeatCreateModel> Seats { get; set; }
}

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



public class CreateRoom
{
    public record Command : ICreateCommand<RoomCreateModel, RoomDto>
    {
        public RoomCreateModel CreateModel { get; init; }
    }
}

public class RoomCrud : IRequestHandler<GetRooms.Query, ResultModel<ListResultModel<RoomDto>>>, IRequestHandler<CreateRoom.Command, ResultModel<RoomDto>>
{
    private readonly IGridRepository<Room> _gridRepository;
    private readonly IMapper _mapper;
    private readonly IRepository<Room> _repository;
    private readonly IRepository<Seat> _repositorySeat;
    public RoomCrud(IMapper mapper, IGridRepository<Room> gridRepository, IRepository<Room> repository, IRepository<Seat> repositorySeat)
    {
        _mapper = mapper;
        _gridRepository = gridRepository;
        _repository = repository;
        _repositorySeat = repositorySeat;
    }

    public async Task<ResultModel<ListResultModel<RoomDto>>> Handle(GetRooms.Query request, CancellationToken cancellationToken)
    {
        var spec = new GetRoomsSpec<RoomDto>(request);
        var rooms = await _gridRepository.FindAsync(spec);
        var totalRooms = await _gridRepository.CountAsync(spec);
        var listResultModel = ListResultModel<RoomDto>.Create(_mapper.Map<List<RoomDto>>(rooms), totalRooms, request.Page, request.PageSize);
        return ResultModel<ListResultModel<RoomDto>>.Create(listResultModel);
    }

    public async Task<ResultModel<RoomDto>> Handle(CreateRoom.Command request, CancellationToken cancellationToken)
    {
        var room = new Room()
        {
            RoomNumber = request.CreateModel.RoomNumber,
        };

        room.Seats = new List<Seat>();
        
        
        foreach (var seatCreateModel in request.CreateModel.Seats)
        {
            room.Seats.Add(new Seat()
            {
                RowNumber = (char)('A' + (seatCreateModel.RowNumber - 1) % 26) + "",
                ColNumber = seatCreateModel.ColNumber.ToString()

            });
            
        }
        await _repository.AddAsync(room);
        return ResultModel<RoomDto>.Create(_mapper.Map<RoomDto>(room));
    }

    
}

public class RoomMapperConfig : Profile
{
    public RoomMapperConfig()
    {
        CreateMap<Room, RoomDto>();
        CreateMap<Seat, SeatDto>();
    }
}