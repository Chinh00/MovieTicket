import {createSlice, PayloadAction} from "@reduxjs/toolkit";
export type AppState = {
    authenticate: boolean
}


export const defaultAppState: AppState = {
    authenticate: false
}


const AppSlice = createSlice({
    name: "app",
    initialState: defaultAppState,
    reducers: {
        ChangeAuthenticate: (action, data: PayloadAction<boolean>) => {
            action.authenticate = data.payload
        }
    }
})


export default AppSlice.reducer
export const {ChangeAuthenticate} = AppSlice.actions