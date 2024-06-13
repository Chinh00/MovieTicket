import {ModuleConfig} from "@/core/config/ModuleConfig.ts";

const moduleConfig: ModuleConfig = {
    authenticate: true, 
    name: "notification", 
    path: "/notification", 
    routes: [
        {
            name: "notification page",
            path: "",
            authenticate: true,
            page: "NotificationPage",
        }
    ]
}

export default moduleConfig