import {
    Box,
    Button,
    IconButton,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow
} from "@mui/material";
import {useGetRooms} from "@/app/usecases/room.usecase.ts";
import dayjs from "dayjs";
import {RiEditBoxLine} from "react-icons/ri";
import {useState} from "react";
import CreateRoom from "@/app/modules/room/components/CreateRoom.tsx";
import { IoIosNotificationsOutline } from "react-icons/io";
const RoomList = () => {
    const {data} = useGetRooms()
    const [onCreate, setOnCreate] = useState(false)
    return <Box
        padding={10}>
        <TableContainer component={Paper} sx={{
            marginTop: "10px"
        }}>
            <Button variant={"contained"} onClick={() => setOnCreate(true)}>Thêm phòng </Button>
            <Table border={2} className={"border-2"} sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>Tên phòng  </TableCell>
                        <TableCell >Tổng số ghế  </TableCell>
                        <TableCell >Hành động  </TableCell>
                    </TableRow> 
                </TableHead>/
                <TableBody>
                    {!!data && data?.data?.data?.items.map((row) => (
                        <TableRow
                            key={row.id}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" scope="row">
                                {row.roomNumber}
                            </TableCell>
                            <TableCell >{dayjs(row?.seats?.length).format("ll")}</TableCell>
                            <TableCell>
                                <IconButton onClick={() => {}} type={"button"}><RiEditBoxLine size={30} /></IconButton>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
        <CreateRoom onCreate={onCreate} setOnCreate={setOnCreate} />
    </Box>
}

export default RoomList