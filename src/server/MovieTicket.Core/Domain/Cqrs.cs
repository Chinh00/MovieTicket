using MediatR;

namespace MovieTicket.Core.Domain;


public interface IQuery<TResponse> : IRequest<ResultModel<TResponse>> where TResponse : notnull {}
public interface ICommand<TResponse> : IRequest<ResultModel<TResponse>> where TResponse : notnull {}

public interface IItemQuery<TId, TResponse> : IQuery<TResponse>
    where TId : notnull
    where TResponse : notnull
{
    public TId Id { get; init; }
}

public interface IListQuery<TResponse> : IQuery<TResponse>
    where TResponse : notnull
{
    
    public List<FilterModel> Filters { get; init; }
    public List<string> Includes { get; init; }
    public List<string> SortBy { get; init; }
    public List<string> SortByDescending { get; init; }
    public int Page { get; init; }
    public int PageSize { get; init; }
    
}



public record FilterModel(string FieldName, string Comparision, string FieldValue);


public record ResultModel<TData>(TData Data, bool IsError, string Message)
{
    public static ResultModel<TData> Create(TData data, bool isError = false, string message = default!) =>
        new ResultModel<TData>(data, isError, message);
}

public record ListResultModel<TData>(List<TData> Items, long TotalItems, int Page, int PageSize)
{
    public static ListResultModel<TData> Create(List<TData> items, long totalItems, int page, int pageSize) =>
        new ListResultModel<TData>(items, totalItems, page, pageSize);
}