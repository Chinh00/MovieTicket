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
import { MdMenuOpen } from "react-icons/md";
import { CgCloseR } from "react-icons/cg";
import {useNavigate} from "react-router-dom";
import {AppbarRouteConfig} from "@/app/layouts/interface/AppbarRouteConfig.ts";

const appbarRouteConfig: AppbarRouteConfig[] = [
    {
        title: "Quản lý rạp phim",
        path: "/room"
    }
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
    const nav = useNavigate()
    
    return <>
        <AppBar>
            <Toolbar>
                <IconButton
                    color="inherit"
                    aria-label="open drawer"
                    onClick={handleDrawerOpen}
                    edge="start"
                    sx={{ mr: 2, ...(open && { display: 'none' }) }}
                >
                    <MdMenuOpen size={30} />
                </IconButton>
                <Typography variant="h6" noWrap component="div">
                    Persistent drawer
                </Typography>
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