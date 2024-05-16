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
            
        }
    ],
    authenticate: "any"
}

export default moduleConfig