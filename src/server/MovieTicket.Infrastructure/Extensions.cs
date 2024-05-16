

using System.Diagnostics;
using MovieTicket.Core.Domain;
using Newtonsoft.Json;
using Newtonsoft.Json.Serialization;

namespace MovieTicket.Infrastructure;

public static class Extensions
{
    [DebuggerStepThrough]
    public static TResult SafeGetListQuery<TResult, TResponse>(this HttpContext httpContext, string query)
        where TResult : IListQuery<TResponse>, new()
    {
        var queryModel = new TResult();
        if (!(string.IsNullOrEmpty(query) || query == "{}"))
        {
            queryModel = JsonConvert.DeserializeObject<TResult>(query);
        }

        httpContext.Response.Headers.Add("x-query",
            JsonConvert.SerializeObject(queryModel,
                new JsonSerializerSettings { ContractResolver = new CamelCasePropertyNamesContractResolver() }));

        return queryModel;
    }
}