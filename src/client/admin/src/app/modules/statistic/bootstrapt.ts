import {ModuleConfig} from "@/core/config/ModuleConfig.ts";

const moduleConfig: ModuleConfig = {
    name: "Statistic",
    path: "",
    routes: [
        {
            name: "Home",
            path: "",
            page: "Home",
            authenticate: true
        }
    ],
    authenticate: true,
}

export default moduleConfig