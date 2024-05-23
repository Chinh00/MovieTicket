import {useQuery} from "react-query";
import http from "@/infrastructure/network/http"
import {ListResponse, SuccessResponse} from "@/infrastructure/utils/response.ts";
import {Room} from "@/domain/entities/room.model.ts";


const GetRooms = async () => {
    return await http.get<SuccessResponse<ListResponse<Room>>>("/api/Room")
}

const useGetRooms = () => {
    return useQuery({
        queryKey: "",
        queryFn: () => GetRooms()
    })
}


export {useGetRooms}