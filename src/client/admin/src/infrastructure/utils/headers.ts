export type ListQuery = {
    filters?: FilterModel,
    includes?: string[],
    sortBy?: string,
    page?: number,
    pageSize?: number
}


export type FilterModel = {
    fieldName: string,
    comparision: string,
    fieldValue: string
}