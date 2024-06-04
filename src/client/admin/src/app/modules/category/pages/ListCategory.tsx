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
import {AppConfig} from "@/core/config/AppConfig.ts";
import {RiEditBoxLine} from "react-icons/ri";
import CreateService from "@/app/modules/service/components/CreateService.tsx";
import {useState} from "react";
import {useGetCategories} from "@/app/usecases/category.usecase.ts";
import CreateCategory from "@/app/modules/category/components/CreateCategory.tsx";

const ListCategory =  () => {
    const [onCreate, setOnCreate] = useState(false)
    const {data, refetch } = useGetCategories({
        
    })
    return <Box
        padding={10}>
        <TableContainer component={Paper} sx={{
            marginTop: "10px"
        }}>
            <Button variant={"contained"} onClick={() => setOnCreate(true)}>Thêm danh mục mới</Button>
            <Table border={2} className={"border-2"} sx={{ minWidth: 650 }} aria-label="simple table">
                <TableHead>
                    <TableRow>
                        <TableCell align={"center"}>Tên danh mục </TableCell>
                        <TableCell>Số phim</TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {!!data && data?.data?.data?.items.map((row) => (
                        <TableRow
                            key={row.id}
                            sx={{ '&:last-child td, &:last-child th': { border: 0 } }}
                        >
                            <TableCell >{row?.name}</TableCell>
                            <TableCell>
                                <IconButton onClick={() => {}} type={"button"}><RiEditBoxLine size={30} /></IconButton>
                            </TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
        <CreateCategory onCreate={onCreate} setOnCreate={setOnCreate} refetch={refetch} />
    </Box>
}

export default ListCategory