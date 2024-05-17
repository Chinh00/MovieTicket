import {EntityBase} from "@/core/domain/EntityBase.ts";
import {Category} from "@/domain/entities/category.model.ts";

export type Movie = {
    name: string,
    releaseDate: Date,
    totalTime: number,
    description: string,
    avatar: string,
    trailer: string
    categories: Category[]
} & EntityBase