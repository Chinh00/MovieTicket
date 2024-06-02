import http from "@/infrastructure/network/http.ts";
import {ListResponse, SuccessResponse} from "@/infrastructure/utils/response.ts";
import {Movie} from "@/domain/entities/movie.model.ts";
import {useMutation, useQuery} from "react-query";
import {XQueryHeader} from "@/infrastructure/network/header.ts";


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

const useGetMovies = (xQuery: XQueryHeader) => {
    return useQuery({
        queryKey: ["movies", xQuery],
        queryFn: () => GetMovies(xQuery)
    })
}

const useCreateMovie = () => {
    return useMutation({
        mutationKey: "create-movie",
        mutationFn: CreateMovie
    })
}


export {useGetMovies, useCreateMovie}