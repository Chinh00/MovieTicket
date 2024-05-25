import {useMutation, useQuery} from "react-query";
import http from "@/infrastructure/network/http"
import {ListResponse, SuccessResponse} from "@/infrastructure/utils/response.ts";
import {Room} from "@/domain/entities/room.model.ts";


const GetRooms = async () => {
    return await http.get<SuccessResponse<ListResponse<Room>>>("/api/Room")
}

export type RoomCreateModel = {
    roomNumber: number,
    seats: SeatCreateModel[]
}

export type SeatCreateModel = {
    rowNumber: number,
    colNumber: number
}

const CreateRoom = async (model: RoomCreateModel) => await http.post<SuccessResponse<Room>>("/api/Room", model)

const useGetRooms = () => {
    return useQuery({
        queryKey: ["rooms"],
        queryFn: () => GetRooms()
    })
}

const useCreateRoom = () => {
    return useMutation({
        mutationKey: ["create-room"],
        mutationFn: CreateRoom
    })
}


export {useGetRooms, useCreateRoom}