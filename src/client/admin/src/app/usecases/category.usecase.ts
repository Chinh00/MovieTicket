import http from "@/infrastructure/network/http.ts";
import {ListResponse, SuccessResponse} from "@/infrastructure/utils/response.ts";
import {Category} from "@/domain/entities/category.model.ts";
import {useQuery} from "react-query";

const GetCategories = async () => await http.get<SuccessResponse<ListResponse<Category>>>("/admin-api/Category")
const useGetCategories = () => {
    return useQuery({
        queryKey: ["categories"],
        queryFn: () => GetCategories()
    })
}


export {useGetCategories}