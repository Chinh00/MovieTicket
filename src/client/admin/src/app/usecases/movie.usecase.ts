import http from "@/infrastructure/network/http.ts";
import {ListResponse, SuccessResponse} from "@/infrastructure/utils/response.ts";
import {Movie} from "@/domain/entities/movie.model.ts";
import {useMutation, useQuery} from "react-query";


const GetMovies = async () => await http.get<SuccessResponse<ListResponse<Movie>>>("/api/Movie")
const CreateMovie = async (data: FormData) => await http.post<SuccessResponse<ListResponse<Movie>>>("/api/Movie", data, {
    headers: {
        "Content-Type": 'multipart/form-data'
    }
})

const useGetMovies = () => {
    return useQuery({
        queryKey: ["movies"],
        queryFn: () => GetMovies()
    })
}

const useCreateMovie = () => {
    return useMutation({
        mutationKey: "create-movie",
        mutationFn: CreateMovie
    })
}


export {useGetMovies, useCreateMovie}