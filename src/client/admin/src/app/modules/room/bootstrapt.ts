import {ModuleConfig} from "@/core/config/ModuleConfig.ts";

const moduleConfig: ModuleConfig = {
    name: "Room",
    path: "/room",
    routes: [
        {
            name: "Room list",
            page: "RoomList",
            path: "",
            authenticate: true
        },
        {
            name: "Screening view",
            page: "RoomScreenings",
            path: ":id/screenings",
            authenticate: true
        }
    ],
    authenticate: true
}

export default moduleConfig