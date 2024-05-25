import {ModuleConfig} from "@/core/config/ModuleConfig.ts";

const moduleConfig: ModuleConfig = {
    name: "Movie",
    path: "/movie",
    routes: [
        {
            name: "movie list",
            page: "MovieList",
            path: "",
            authenticate: "any",
        }  
    ],
    authenticate: "any"
}

export default moduleConfig