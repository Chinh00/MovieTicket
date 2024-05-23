import {useMutation, useQuery} from "react-query";
import http from "@/infrastructure/network/http.ts";
import {ListResponse, SuccessResponse} from "@/infrastructure/utils/response.ts";
import {Screening} from "@/domain/entities/screening.model.ts";
import {IListQuery} from "@/infrastructure/utils/request.ts";


export type CreateScreeningModel = {
    [k in keyof Pick<Screening, "movieId" | "roomId" | "startDate">]: Screening[k]
}


const GetScreenings = async (query: IListQuery) => await http.get<SuccessResponse<ListResponse<Screening>>>("/admin/api/Screening", {
    headers: {
        "x-query": JSON.stringify(query)
    }
})

const CreateScreening = async (model: CreateScreeningModel) => await http.post<SuccessResponse<string>>("/admin/api/Screening", model)


const useCreateScreening = () => {
    return useMutation({
        mutationKey: "create-screening",
        mutationFn: CreateScreening
    })
}


const useGetScreenings = (query: IListQuery, enabled: boolean) => {
    return useQuery({
        queryKey: ["screenings", query],
        queryFn: () => GetScreenings(query), 
        enabled: enabled
    })
}

export {useGetScreenings, useCreateScreening}