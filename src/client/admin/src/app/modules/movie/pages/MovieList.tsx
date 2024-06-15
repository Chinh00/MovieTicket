import {
    Box,
    Button, Drawer,
    IconButton, lighten, ListItemIcon, MenuItem,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow, Typography
} from "@mui/material";
import {useGetMovies} from "@/app/usecases/movie.usecase.ts";
import dayjs from "dayjs";
import { RiEditBoxLine } from "react-icons/ri";
import {useEffect, useMemo, useState} from "react";
import CreateMovie from "@/app/modules/movie/components/CreateMovie.tsx";
import {IoIosNotificationsOutline} from "react-icons/io";
import {
    MaterialReactTable,
    MRT_ColumnDef,
    MRT_GlobalFilterTextField,
    MRT_ToggleFiltersButton,
    useMaterialReactTable
} from "material-react-table";
import {Movie} from "@/domain/entities/movie.model.ts";
import {AccountCircle, Send} from "@mui/icons-material";
import {XQueryHeader} from "@/infrastructure/network/header.ts";
import {AppConfig} from "@/core/config/AppConfig.ts";

const MovieList = () => {

    const [onEdit, setOnEdit] = useState<string>("")
    const [onCreate, setOnCreate] = useState(false)
    const [pagination, setPagination] = useState({
        pageIndex: 0,
        pageSize: 5
    })
   
    const {data, isLoading} = useGetMovies({
        page: (pagination.pageSize * (pagination.pageIndex )) + 1,
        pageSize: pagination.pageSize
    })

    
    const columns = useMemo<MRT_ColumnDef<Movie>[]>(
        () => [
            {
                accessorKey: 'name',
                header: 'Tên phim',
            },
            {
                accessorKey: 'releaseDate',
                header: 'Ngày phát hành',
                Cell: props => (<span>{dayjs(props?.renderedCellValue?.toString()).format("ll")}</span>)
            },
            {
                accessorKey: 'totalTime',
                header: 'Tổng thời gian chiếu (phút)',
            },
            
        ],
        [],
    );
    const res = data?.data?.data?.items || []

    const handlePaginationChange = (updater: any) => {
        setPagination((old) => {
            const newState = typeof updater === 'function' ? updater(old) : updater;
            return newState;
        });
    };
    
    
    
    const table = useMaterialReactTable({
        columns,
        data:res,
        enableColumnFilterModes: false,
        enableColumnOrdering: false,
        enableGrouping: false,
        enableColumnPinning: true,
        enableFacetedValues: true,
        enableRowActions: true,
        enableRowSelection: true,
        initialState: {
            columnPinning: {
                left: ['mrt-row-expand', 'mrt-row-select'],
                right: ['mrt-row-actions'],
            },
        },
        state: {
            showSkeletons: isLoading,
            pagination: pagination
        },
        paginationDisplayMode: 'pages',
        positionToolbarAlertBanner: 'bottom',
        muiSearchTextFieldProps: {
            size: 'small',
            variant: 'outlined',
        },
        muiPaginationProps: {
            color: 'secondary',
            rowsPerPageOptions: [5, 10, 20, 30],
            shape: 'rounded',
            variant: 'outlined',
        },
        pageCount: Math.ceil(Number.parseInt((data?.data?.data?.totalItems ?? 1).toString()) / Number.parseInt((data?.data?.data?.pageSize ?? 1).toString())),
        rowCount: pagination.pageSize,
        manualPagination: true,
        renderDetailPanel: ({ row }) => (
            <Box
                sx={{
                    alignItems: 'center',
                    display: 'flex',
                    justifyContent: 'space-around',
                    left: '30px',
                    maxWidth: '1000px',
                    position: 'sticky',
                    width: '100%',
                }}
            >
                <img
                    alt="avatar"
                    width={200}
                    height={300}
                    
                    src={ `${ import.meta.env.DEV ? "http://localhost:5002" : ""}${row?.original?.avatar}`}
                    loading="lazy"
                />
                <Box sx={{ textAlign: 'center' }}>
                    
                </Box>
            </Box>
        ),
        onPaginationChange: handlePaginationChange
    });
    
    
    
    
    return <Box padding={5}>
        <Button variant={"contained"} onClick={() => setOnCreate(true)}>Thêm phim mới </Button>

        <MaterialReactTable table={table} />
        
        
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