export type XQueryHeader = {
    includes?: string[],
    filters?: FilterModel[],
    sortBy?: string[],
    page?: number,
    pageSize?: number
}
export type FilterModel = {
    fieldName: string,
    comparision: string,
    fieldValue: string
}

