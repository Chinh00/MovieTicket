import {useEffect, useState} from "react";
import Application from "@/app/init/Application.ts";
import {defaultResource} from "@/core/config/ModuleConfig.ts";
import {BrowserRouter} from "react-router-dom";
import RouterProvider from "@/app/provider/RouterProvider.tsx";
import {CssBaseline} from "@mui/material";
import ReactQueryProvider from "@/app/provider/ReactQueryProvider.tsx";
import 'nprogress/nprogress.css';
import * as nProgress from "nprogress";
import {LocalizationProvider} from "@mui/x-date-pickers";
import {AdapterDayjs} from '@mui/x-date-pickers/AdapterDayjs';
nProgress.configure({
    showSpinner: true,
})
const App = () => {
    const [init, setInit] = useState(defaultResource)
    useEffect(() => {
        Application.Init().then(t => {
            setInit(prevState => ({...t}))
            console.log(t)
        })
    }, []);
    return !init.init ? <div>Loading ...</div>  : <BrowserRouter>
        <LocalizationProvider dateAdapter={AdapterDayjs}>
            <CssBaseline />
            <ReactQueryProvider>
                <RouterProvider routes={init.routes} />
            </ReactQueryProvider>
        </LocalizationProvider>
    </BrowserRouter>
}

export default App