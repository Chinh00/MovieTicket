import {
    Box,
    Button, Drawer,
    IconButton,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow
} from "@mui/material";
import {useGetMovies} from "@/app/usecases/movie.usecase.ts";
import dayjs from "dayjs";
import { RiEditBoxLine } from "react-icons/ri";
import {useEffect, useState} from "react";
import CreateMovie from "@/app/modules/movie/components/CreateMovie.tsx";
import {IoIosNotificationsOutline} from "react-icons/io";
const MovieList = () => {

    const [onEdit, setOnEdit] = useState<string>("")
    const [onCreate, setOnCreate] = useState(false)
    const {data} = useGetMovies()
    return <Box padding={10}>
        <Button variant={"contained"} onClick={() => setOnCreate(true)}>Thêm phim mới </Button>
        <TableContainer component={Paper} sx={{
            marginTop: "10px"
        }}>
            <Table border={2} className={"border-2"} sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell>Tên phim </TableCell>
                        <TableCell >Ngày phát hành </TableCell>
                        <TableCell >Tổng thời lượng </TableCell>
                        <TableCell >Ảnh </TableCell>
                        <TableCell >Thể loại </TableCell>
                        <TableCell >Hành động  </TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {!!data && data?.data?.data?.items.map((row) => (
                        <TableRow
                            key={row.id}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell component="th" scope="row">
                                {row.name}
                            </TableCell>
                            <TableCell >{dayjs(row?.releaseDate).format("ll")}</TableCell>
                            <TableCell >{row?.totalTime}</TableCell>
                            <TableCell >
                                <Box width={200} height={300}>
                                    {
                                        import.meta.env.DEV ? <img className={"w-full h-full"} src={`http://localhost:5002${row?.avatar}`}/> : <img src={row?.avatar}/>
                                    }
                                </Box>
                            </TableCell>
                            <TableCell >
                                <Box className={"flex flex-row flex-wrap gap-2"}>
                                    {
                                        row.categories?.length > 0 ? row.categories?.map((item) => {
                                            return <Button variant={"outlined"} key={item?.id}>{item?.name}</Button>
                                        }) : "__"
                                    }
                                </Box>
                            </TableCell>
                            <TableCell>
                                <IconButton color={"info"} onClick={() => setOnEdit(row.id)} type={"button"}><RiEditBoxLine size={30} /></IconButton>
                                <IconButton color={'warning'} onClick={() => {}} type={"button"}><IoIosNotificationsOutline size={30} /></IconButton>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
        
        <Drawer
            anchor={"right"}
            open={!!onEdit}
            onClose={() => setOnEdit("")}
        >
            <Box
                sx={{width: "500px"}}
            >
                
            </Box>
        </Drawer>
        
        <CreateMovie onCreate={onCreate} setOnCreate={setOnCreate} />
        
        
    </Box>
}


export default MovieList