using Microsoft.AspNetCore.Mvc;
using Microsoft.EntityFrameworkCore;
using MovieTicketClient.Application.Data;

namespace MovieTicketClient.Api.Controller;

public class SeatPlaceController : ControllerBase
{
    private readonly DbContextMemory _contextMemory;

    public SeatPlaceController()
    {
        var options = new DbContextOptionsBuilder<DbContextMemory>()
            .UseInMemoryDatabase("SeatPlace")
            .Options;
        _contextMemory = new DbContextMemory(options);
    }

    [HttpPost("lock")]
    public async Task<IActionResult> HandleLockSeatAsync(SeatLockModel model, CancellationToken cancellationToken = new CancellationToken())
    {
        var seatLock = await _contextMemory.Set<SeatPlace>().AddAsync(new SeatPlace()
        {
            Id = Guid.NewGuid(),
            RoomId = model.RoomId,
            ScreeningId = model.ScreeningId,
            SeatId = model.SeatId,
            IsPlace = true
        }, cancellationToken);
        await _contextMemory.SaveChangesAsync(cancellationToken);
        return Ok(seatLock.Entity.Id);
    }
    
    [HttpPost("unlock")]
    public async Task<IActionResult> HandleUnLockSeatAsync(Guid lockId, CancellationToken cancellationToken = new CancellationToken())
    {
        var seatLock = await _contextMemory.Set<SeatPlace>().FirstOrDefaultAsync(e => e.Id == lockId, cancellationToken: cancellationToken);
        _contextMemory.Remove(seatLock);
        await _contextMemory.SaveChangesAsync(cancellationToken);
        return Ok(seatLock);
    }

    [HttpGet("screening/{screeningId:guid}/room/{roomId:guid}/seat/{seatId:guid}")]
    public async Task<IActionResult> HandleGetSeatLockAsync(
        Guid screeningId, Guid roomId, Guid seatId, CancellationToken cancellationToken = new CancellationToken()
        )
    {
        var seatLock = await _contextMemory.Set<SeatPlace>().FirstOrDefaultAsync(e => e.ScreeningId == screeningId && e.RoomId == roomId && e.SeatId == seatId, cancellationToken: cancellationToken);
        return Ok(seatLock);
    }
}

public class SeatLockModel
{
    public Guid RoomId { get; set; }
    public Guid SeatId { get; set; }
    public Guid ScreeningId { get; set; }
}