import {memo, useState} from "react";
import {
    AppBar, Button,
    Divider,
    Drawer,
    IconButton,
    List,
    ListItem,
    ListItemButton, ListItemIcon, ListItemText, styled,
    Toolbar,
    Typography,
    useTheme
} from "@mui/material";
import {MdMenuOpen, MdOutlineLogout} from "react-icons/md";
import { CgCloseR } from "react-icons/cg";
import {useNavigate} from "react-router-dom";
import {AppbarRouteConfig} from "@/app/layouts/interface/AppbarRouteConfig.ts";
import {useAppDispatch, useAppSelector} from "@/app/stores/hook.ts";
import {IoCloseCircleOutline, IoLogOutSharp} from "react-icons/io5";
import {useLogoutDefault} from "@/app/usecases/auth.usecase.ts";
import {LoadingButton} from "@mui/lab";
import {ChangeAuthenticate} from "@/app/stores/app/AppSlice.ts";

const appbarRouteConfig: AppbarRouteConfig[] = [
    {
        title: "Quản lý lịch chiếu phim ",
        path: "/screening"
    },
    {
        title: "Quản lý phim",
        path: "/movie"
    },
    {
        title: "Cấu hình phòng ",
        path: "/room"
    },
    {
        title: "Cấu hình dịch vụ  ",
        path: "/service"
    },
    
    
]

const AppBarCustom = () => {
    const theme = useTheme();
    const [open, setOpen] = useState(false);

    const handleDrawerOpen = () => {
        setOpen(true);
    };

    const handleDrawerClose = () => {
        setOpen(false);
    };
    const {mutate, isLoading} = useLogoutDefault()  
    const dispatch = useAppDispatch()
    
    
    
    const nav = useNavigate()
    const {authenticate} = useAppSelector(e => e.app)
    return <>
        <AppBar color={"default"} position={"sticky"} >
            <Toolbar className={"w-full flex justify-between"}>
                {authenticate && <IconButton
                    color="inherit"
                    aria-label="open drawer"
                    onClick={handleDrawerOpen}
                    edge="start"
                    sx={{ mr: 2, ...(open && { display: 'none' }) }}
                >
                    <MdMenuOpen size={30} />
                </IconButton>}
                <Typography variant="h6" noWrap component="div" align={"center"}>
                    Quản lý rạp phim 
                </Typography>
                <LoadingButton loading={isLoading}
                               onClick={() => {
                                   mutate()
                                   dispatch(ChangeAuthenticate(false))
                               }}
                               variant={"outlined"} className={"float-right"}><MdOutlineLogout color={"red"} size={30} /></LoadingButton>
            </Toolbar>
        </AppBar>
        <Drawer
            onClose={handleDrawerClose}
            open={open}
            sx={{pr: 10}}
        >
            
            <Divider />
            <List className={"w-[240px]"}>
                <ListItem className={"flex justify-end"}>
                    <Button onClick={handleDrawerClose} className={"pr-0"}><CgCloseR size={30} className={""} /></Button>
                    
                </ListItem>
                {appbarRouteConfig.map(({title, path}, index) => (
                    <ListItem key={index}>
                        <ListItemButton onClick={() => {
                            nav(path)
                            handleDrawerClose()
                        }} className={"text-center p-3"}>{title}</ListItemButton>
                    </ListItem>
                ))}
            </List>
        </Drawer></>
}


export default memo(AppBarCustom)