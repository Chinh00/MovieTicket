using MediatR;
using Microsoft.AspNetCore.Mvc;

namespace MovieTicket.Infrastructure.Controller;

[ApiController]
public class BaseController : ControllerBase
{
    private ISender _mediator;
    protected ISender Mediator => _mediator ??= HttpContext.RequestServices.GetService<ISender>();
}