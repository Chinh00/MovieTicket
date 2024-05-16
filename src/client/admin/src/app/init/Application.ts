import {defaultResource, ModuleConfig, Resource} from "@/core/config/ModuleConfig.ts";
import {cloneDeep} from "lodash";
import Path from "@/infrastructure/utils/Path.ts";

class Application {
    public static async Init() {
        const resource = cloneDeep(defaultResource)
        const modules = (await import("@/app/modules/index.ts")).default
        for (let i = 0; i < modules.length; i++) {
            const {name, layout, routes, authenticate, path}: ModuleConfig = (await import((`../modules/${modules[i]}/bootstrapt.ts`))).default
            routes.forEach((t) => {
                resource.routes.push({
                    name: t.name,
                    layout: t.layout || layout || "DefaultLayout",
                    path: `/${Path.trim(`${path}/${t.path}`)}`,
                    page: t.page,
                    authenticate: t.authenticate,
                    module: modules[i]
                })
            })
            
        }
        resource.init = true
        return resource
    }
}
export default Application