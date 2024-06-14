## Restful api Movie ticket 



| api endpoint                  | server                            |
|-------------------------------|-----------------------------------|
| /client-api/{**reminder}      | api for client (mobile)           |
| /client-identity/{**reminder} | identity server for client mobile |
| /admin-api/{**reminder}       | api for admin ui (reactjs)        |
| /admin-identity/{**reminder}  | identity server for admin         |
| /                             | ui for admin dashboard            |





```csharp
    server response api fomarter
    var item-response = {
        data: T,
        isError: boolean,
        message: string
    }
    
    var list-response = {
        data: {
            items: T[],
            totalItems: number,
            page: number,
            pageSize: number
        },
        isError: boolean,
        messsage: string
    }
```


```csharp
    request query api
    query item: /endpoint/{Entity}/{id}
    query list:
        pass query header:
            headers: {
                ...,
                "x-query": {"filters":[],"includes":["Categories"],"sortBy":["Id"],"page":number,"pageSize":number}
            }
        explain: 
            - filters: 
                pass conditions where (and)
                filters: [
                    {
                        fieldName: string,
                        comparision: ">" | "<" | "=" | "in" | "contains",
                        fieldValue: string
                    },
                    ...
                ]
            - includes: 
                inner join 
                includes: <string>[
                    "..."
                ]
            - sortBy: 
                sort by field name (default: DESC)
                sortBy: <string>[
                    ...,
                    "Id" or "IdAsc" (if sort ASC),
                ]
            page, pageSize ...
```