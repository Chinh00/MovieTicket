import http from "@/infrastructure/network/http.ts";
import {useMutation} from "react-query";


export type NotificationNewMovieModel = {
    movieId: string,
    message: string,
    sendTime: Date
}

const CreateNotificationNewMovie = async (model: NotificationNewMovieModel) => await http.post("/notification-api/Notification", model)

const useCreateNotificationNewMovie = ( ) => {
    return useMutation({
        mutationKey: "create new notification new movie",
        mutationFn: CreateNotificationNewMovie
    })
}

export {useCreateNotificationNewMovie}