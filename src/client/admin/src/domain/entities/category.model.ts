import {EntityBase} from "@/core/domain/EntityBase.ts";
import {Movie} from "@/domain/entities/movie.model.ts";

export type Category = {
    name: string,
    movies: Movie[]
} & EntityBase