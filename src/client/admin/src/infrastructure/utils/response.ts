export type SuccessResponse<T> = {
    data: T,
    isError: boolean,
    message: string
} 

export type ListResponse<T> = {
    items: T[],
    totalItems: number,
    page: number,
    pageSize: number
}