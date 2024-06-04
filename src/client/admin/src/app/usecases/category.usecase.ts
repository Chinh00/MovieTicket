import http from "@/infrastructure/network/http.ts";
import {ListResponse, SuccessResponse} from "@/infrastructure/utils/response.ts";
import {Category} from "@/domain/entities/category.model.ts";
import {useMutation, useQuery} from "react-query";
import {IListQuery} from "@/infrastructure/utils/request.ts";

const GetCategories = async (listQuery: IListQuery) => await http.get<SuccessResponse<ListResponse<Category>>>("/admin-api/Category", {
    headers: {
        "x-query": JSON.stringify(listQuery)
    }
})

export type CategoryCreateModel = {
    name: string
}

const CreateCategory = async (model: CategoryCreateModel) => {
    return await http.post<SuccessResponse<Category>>("/admin-api/Category", model)
}

const useCreateCategory = () => {
    return useMutation({
        mutationKey: "create-category",
        mutationFn: CreateCategory
    })
}

const useGetCategories = (listQuery: IListQuery) => {
    return useQuery({
        queryKey: ["categories"],
        queryFn: () => GetCategories(listQuery)
    })
}


export {useGetCategories, useCreateCategory}