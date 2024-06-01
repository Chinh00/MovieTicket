
import storage from 'redux-persist/lib/storage'
import {persistReducer, persistStore} from "redux-persist"
import {configureStore} from "@reduxjs/toolkit";
import appReducer from "@/app/stores/app/AppSlice.ts"

export const store = configureStore({
    reducer: {
       app: persistReducer({
           storage: storage,
           key: "app",
           blacklist: []
       }, appReducer)
    },
    middleware: getDefaultMiddleware => getDefaultMiddleware({
        serializableCheck: false
    })
})

export type RootState = ReturnType<typeof store.getState>
// Inferred type: {posts: PostsState, comments: CommentsState, users: UsersState}
export type AppDispatch = typeof store.dispatch


export const persistor = persistStore(store)