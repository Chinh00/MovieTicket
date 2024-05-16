export interface ModuleConfig {
    name: string,
    routes: RouteConfig[]
    path: string,
    layout?: string,
    authenticate: boolean | "any"
}

export interface RouteConfig {
    name: string,
    path: string,
    page: string,
    layout?: string,
    authenticate: boolean | "any"
}

export interface Resource {
    init: boolean,
    routes: ResourceRoute[],
}

export interface ResourceRoute extends RouteConfig {
    module: string
}

export const defaultResource: Resource = {
    init: false,
    routes: []
    
}
