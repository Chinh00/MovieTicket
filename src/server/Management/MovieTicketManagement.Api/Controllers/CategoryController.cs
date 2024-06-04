using Microsoft.AspNetCore.Authorization;
using Microsoft.AspNetCore.Mvc;
using MovieTicket.Core.Domain;
using MovieTicket.Infrastructure;
using MovieTicket.Infrastructure.Controller;
using MovieTicketManagement.Application.Usecases.CategoryCRUD;

namespace MovieTicketManagement.Api.Controllers;

/// <inheritdoc />

[Authorize]
public class CategoryController : BaseController
{
    /// <summary>
    /// Get Movies
    /// </summary>
    /// <param name="query"></param>
    /// <param name="cancellationToken"></param>
    /// <returns></returns>
    [HttpGet]
    public async Task<IActionResult> HandleGetCategoriesAsync([FromHeader(Name = "x-query")] string query, CancellationToken cancellationToken = new ())
    {
        var model = HttpContext.SafeGetListQuery<GetCategories.Query, ListResultModel<CategoryDto>>(query);
        return Ok(await Mediator.Send(model, cancellationToken ));
    }

    /// <summary>
    /// Create category
    /// </summary>
    /// <param name="model"></param>
    /// <param name="cancellationToken"></param>
    /// <returns></returns>
    [HttpPost]
    public async Task<IActionResult> HandleCreateCategoryAsync(CreateCategoryModel model, CancellationToken cancellationToken = new ())
    {
        return Ok(await Mediator.Send(new CategoryCreate.Command() { CreateModel = model }, cancellationToken));
    }
}