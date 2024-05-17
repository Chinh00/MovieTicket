import {memo} from "react";
import {Room} from "@/domain/entities/room.model.ts";
import {Box, Card} from "@mui/material";
import {useNavigate} from "react-router-dom";

export type RoomItemProps = {
    
} & Room

const RoomItem = (props: RoomItemProps) => {
    const nav = useNavigate()
    return <div onClick={() => nav(`/room/${props.id}/screenings`)} className={"w-[150px] font-bold rounded-md flex justify-center items-center flex-col h-[200px] text-white border-2 p-2 bg-black backdrop-blur opacity-60 cursor-pointer shadow-xl hover:-translate-y-[10px] transition-all"}>
        <p>Phòng chiếu {props?.roomNumber}</p>
        <div>Tổng số ghế: {props?.seats?.length}</div>
    </div>
}

export default memo(RoomItem)