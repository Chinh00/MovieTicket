import http from "@/infrastructure/network/http.ts";
import {ListResponse, SuccessResponse} from "@/infrastructure/utils/response.ts";
import {Movie} from "@/domain/entities/movie.model.ts";
import {useQuery} from "react-query";


const GetMovies = async () => await http.get<SuccessResponse<ListResponse<Movie>>>("/admin/api/Movie")

const useGetMovies = () => {
    return useQuery({
        queryKey: ["movies"],
        queryFn: () => GetMovies()
    })
}

export {useGetMovies}