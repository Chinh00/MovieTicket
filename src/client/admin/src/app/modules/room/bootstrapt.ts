import {ModuleConfig} from "@/core/config/ModuleConfig.ts";

const moduleConfig: ModuleConfig = {
    name: "Room",
    path: "/room",
    routes: [
        {
            name: "Room view",
            page: "RoomPage",
            path: "",
            authenticate: "any"
        },
        {
            name: "Screening view",
            page: "RoomScreenings",
            path: ":id/screenings",
            authenticate: "any"
        }
    ],
    authenticate: "any"
}

export default moduleConfig