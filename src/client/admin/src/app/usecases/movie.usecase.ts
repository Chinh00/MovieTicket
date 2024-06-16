import http from "@/infrastructure/network/http.ts";
import {ListResponse, SuccessResponse} from "@/infrastructure/utils/response.ts";
import {Movie} from "@/domain/entities/movie.model.ts";
import {QueryObserverOptions, useMutation, useQuery, UseQueryOptions, UseQueryResult} from "react-query";
import {XQueryHeader} from "@/infrastructure/network/header.ts";
import {AxiosResponse} from "axios";


const GetMovies = async (xQuery: XQueryHeader) => await http.get<SuccessResponse<ListResponse<Movie>>>("/admin-api/Movie", {
    headers: {
        "x-query": JSON.stringify(xQuery)
    }
})
const CreateMovie = async (data: FormData) => await http.post<SuccessResponse<ListResponse<Movie>>>("/admin-api/Movie", data, {
    headers: {
        "Content-Type": 'multipart/form-data'
    }
})

const UpdateMovie = async ({ movieId, data}: {movieId: string, data: FormData}) => await http.put<SuccessResponse<Movie>>(`/admin-api/Movie/${movieId}`, data, {
    headers: {
        "Content-Type": 'multipart/form-data'
    }
})
const useGetMovies = (xQuery: XQueryHeader, options?: QueryObserverOptions<AxiosResponse<SuccessResponse<ListResponse<Movie>>>>): UseQueryResult<AxiosResponse<SuccessResponse<ListResponse<Movie>>>, any> => {
    return useQuery({
        queryKey: ["movies", xQuery],
        queryFn: () => GetMovies(xQuery),
        ...options
        
    })
}

const useUpdateMovie = () => {
    return useMutation({
        mutationKey: "update-move",
        mutationFn: UpdateMovie
    })
}

const useCreateMovie = () => {
    return useMutation({
        mutationKey: "create-movie",
        mutationFn: CreateMovie
    })
}


export {useGetMovies, useCreateMovie, useUpdateMovie}