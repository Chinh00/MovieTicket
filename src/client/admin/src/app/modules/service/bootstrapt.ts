import {ModuleConfig} from "@/core/config/ModuleConfig.ts";

const moduleConfig: ModuleConfig = {
    name: "service",
    authenticate: true,
    path: "/service",
    routes: [
        {
            authenticate: true,
            path: "",
            name: "list services",
            page: "ListService"
        }
    ]
}

export default moduleConfig