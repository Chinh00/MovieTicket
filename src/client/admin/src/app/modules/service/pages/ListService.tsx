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
import dayjs from "dayjs";
import {RiEditBoxLine} from "react-icons/ri";
import CreateRoom from "@/app/modules/room/components/CreateRoom.tsx";
import {useGetServices} from "@/app/usecases/service.usecase.ts";
import {useState} from "react";
import CreateService from "@/app/modules/service/components/CreateService.tsx";
import {AppConfig} from "@/core/config/AppConfig.ts";

const ListService = () => {
    const {data, refetch} = useGetServices()
    const [onCreate, setOnCreate] = useState(false)
    
    
    return <Box
        padding={10}>
        <TableContainer component={Paper} sx={{
            marginTop: "10px"
        }}>
            <Button variant={"contained"} onClick={() => setOnCreate(true)}>Thêm dịch vụ  </Button>
            <Table border={2} className={"border-2"} sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell align={"center"}>Hình ảnh </TableCell>
                        <TableCell>Tên dịch vụ </TableCell>
                        <TableCell > Đơn vị </TableCell>
                        <TableCell >Đơn giá</TableCell>
                        <TableCell >Hành động</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {!!data && data?.data?.data?.items.map((row) => (
                        <TableRow
                            key={row.id}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell align={"center"}>
                                <img className={"w-[200px] mx-auto h-[200px] object-cover"} src={`${AppConfig.BASE_URL}/admin-api/image${row?.avatar}`}/>
                            </TableCell>
                            <TableCell >{row?.name}</TableCell>
                            <TableCell >{row?.unit}</TableCell>
                            <TableCell >{row?.priceUnit}</TableCell>
                            <TableCell>
                                <IconButton onClick={() => {}} type={"button"}><RiEditBoxLine size={30} /></IconButton>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
        <CreateService onCreate={onCreate} setOnCreate={setOnCreate} refetch={refetch} />
    </Box>
}

export default ListService