import {useGetRooms} from "@/app/usecases/room.usecase.ts";
import {Box, Typography} from "@mui/material";
import RoomItem from "@/app/modules/screening/components/RoomItem.tsx";

const ScreeningView = () => {
    const {data} = useGetRooms()

    return <Box className={"mt-10"}>
        <Typography align={"center"} variant={"h4"}>Danh sách phòng chiếu </Typography>
        <Box padding={5} display={"flex"} gap={4} justifyContent={"start"} flexWrap={"wrap"}>
            {data?.data?.data?.items?.map((value, index) => {
                return <RoomItem key={index} {...value} />
            })}

        </Box>
    </Box>
}

export default ScreeningView