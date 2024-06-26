using System.Linq.Expressions;
using MovieTicket.Core.Specification;

namespace MovieTicketClient.Application.Usecases.Reservation.Specs;

public class GetReservationByScreeningIdSpec : SpecificationBase<MovieTicket.Domain.Entities.Reservation>
{
    private Guid ScreeningId { get; set; }

    public GetReservationByScreeningIdSpec(Guid screeningId)
    {
        ScreeningId = screeningId;
        AddInclude(e => e.SeatReservations);
    }

    public override Expression<Func<MovieTicket.Domain.Entities.Reservation, bool>> Criteria
    {
        get
        {
            return e => e.ScreeningId == ScreeningId;
        }
    }
}