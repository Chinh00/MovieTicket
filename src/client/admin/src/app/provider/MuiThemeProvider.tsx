import {createTheme, ThemeProvider} from "@mui/material";
import {ReactNode} from "react";

const MuiThemeProvider = ({children}: {children: ReactNode}) => {
    const theme = createTheme({
        components: {
            MuiTableCell: {
                styleOverrides: {
                    alignJustify: "center"
                }
            }
        }
    })
    return <ThemeProvider theme={theme}>
        {children}
    </ThemeProvider>
}

export default MuiThemeProvider