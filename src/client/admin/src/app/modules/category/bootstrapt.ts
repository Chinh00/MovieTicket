import {ModuleConfig} from "@/core/config/ModuleConfig.ts";

const moduleConfig: ModuleConfig = {
    name: "category",
    authenticate: true,
    path: "category",
    routes: [
        {
            name: "list category",
            authenticate: true,
            path: "",
            page: "ListCategory"
        }
    ]
}

export default moduleConfig