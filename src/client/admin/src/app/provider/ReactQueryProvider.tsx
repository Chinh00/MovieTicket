import {ReactNode} from "react";
import {QueryClient, QueryClientProvider} from "react-query";
import {ReactQueryDevtools} from "react-query/devtools";

const ReactQueryProvider = ({children}: {children: ReactNode}) => {
    const queryClient = new QueryClient({
        defaultOptions: {
            queries: {
                staleTime: Infinity,
                retry: 3,
                cacheTime: 0
            },
            mutations: {}
        }
    })
    return <>
        <QueryClientProvider client={queryClient}>
            {children}
            <ReactQueryDevtools />
        </QueryClientProvider>
    </>
}
export default ReactQueryProvider