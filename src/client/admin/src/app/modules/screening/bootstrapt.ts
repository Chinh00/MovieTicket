import {ModuleConfig} from "@/core/config/ModuleConfig.ts";

const moduleConfig: ModuleConfig = {
    name: "Screening",
    path: "screening",
    routes: [
        {
            name: "Screening View",
            page: "ScreeningView",
            path: "/",
            authenticate: "any"
            
        },
        {
            name: "Screening View",
            page: "RoomScreenings",
            path: ":id/add-movie",
            authenticate: "any"
            
        },
        
    ],
    authenticate: true
}

export default moduleConfig