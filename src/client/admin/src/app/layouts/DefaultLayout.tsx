import AppBarCustom from "@/app/layouts/common/AppBarCustom.tsx";
import {Outlet} from "react-router-dom";
import Footer from "@/app/layouts/common/Footer.tsx";
import {Box} from "@mui/material";

const DefaultLayout = () => {
    return <Box>
        <AppBarCustom />
        <Outlet />
        <Footer />
    </Box>
}

export default DefaultLayout