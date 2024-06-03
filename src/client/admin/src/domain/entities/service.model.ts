import {EntityBase} from "@/core/domain/EntityBase.ts";

export type Service = {
    name: string,
    unit: string,
    priceUnit: number,
    avatar: string
} & EntityBase