import http from "@/infrastructure/network/http.ts";
import {ListResponse, SuccessResponse} from "@/infrastructure/utils/response.ts";
import {useMutation, useQuery} from "react-query";
import {Service} from "@/domain/entities/service.model.ts";

const GetServices = async () => await http.get<SuccessResponse<ListResponse<Service>>>("/admin-api/Service")


const CreateService = async (form: FormData) => await http.post("/admin-api/Service", form)
    

export type ServiceCreateModel = {
    [k in keyof Omit<Service, "id" | "createDate" | "updateDate">]: Service[k]
}

const useCreateService = () => {
    return useMutation({
        mutationKey: "service-create",
        mutationFn: CreateService
    })
}
    
    
const useGetServices = () => {
    return useQuery({
        queryKey: ["categories"],
        queryFn: () => GetServices()
    })
}

export {useGetServices, useCreateService}