import {configureStore} from "@reduxjs/toolkit";
import appReducer from "@/app/stores/app/AppSlice.ts"
import storage from 'redux-persist/lib/storage'
import {persistReducer, persistStore} from "redux-persist"
export const store = configureStore({
    reducer: {
       app: persistReducer({
           key: "app",
           storage: storage,
           blacklist: []
       }, appReducer)
    },
    middleware: getDefaultMiddleware => getDefaultMiddleware({
        serializableCheck: false
    })
})

export type RootState = ReturnType<typeof store.getState>
export type AppDispatch = typeof store.dispatch

export const persistor = persistStore(store)