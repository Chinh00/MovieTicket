import {EntityBase} from "@/core/domain/EntityBase.ts";
import {Movie} from "@/domain/entities/movie.model.ts";
import {Room} from "@/domain/entities/room.model.ts";


export type Screening = {
    movieId: string,
    roomId: string,
    startDate: Date,
    movie: Movie
    room: Room
    
} & EntityBase