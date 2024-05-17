import {EntityBase} from "@/core/domain/EntityBase.ts";
import {Seat} from "@/domain/entities/seat.model.ts";

export type Room = {
    roomNumber: string,
    seats: Seat[]
} & EntityBase