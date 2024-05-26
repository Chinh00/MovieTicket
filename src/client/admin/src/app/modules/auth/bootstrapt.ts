import {ModuleConfig} from "@/core/config/ModuleConfig.ts";

const moduleConfig: ModuleConfig = {
    name: "Authenticate",
    authenticate: "any",
    path: "",
    routes: [
        {
            name: "Login page",
            path: "/login",
            authenticate: "any",
            page: "LoginPage",
            
        }
    ],
}

export default moduleConfig