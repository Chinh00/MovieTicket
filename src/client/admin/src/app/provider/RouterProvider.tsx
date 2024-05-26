import {Navigate, Outlet, Route, Routes} from "react-router-dom";
import {ResourceRoute} from "@/core/config/ModuleConfig.ts";
import loadable from "@loadable/component"
import {Suspense} from "react";
import {useAppSelector} from "@/app/stores/hook.ts";

const ProtectedRoute = () => {
    const {authenticate} = useAppSelector(e => e.app)
    return authenticate ? <Outlet /> : <Navigate to={"/login"} />
}


const RouterProvider = ({routes}: {routes: ResourceRoute[]}) => {
    return <Routes>
        {routes.map( (value, index) => {
            const LazyComponent = loadable(() => import((`../modules/${value.module}/pages/${value.page}.tsx`)))
            const LazyLayout = loadable(() => import((`../layouts/${value.layout}.tsx`)))
            return <Route  key={index} element={<Suspense> <LazyLayout /> </Suspense>}>
                {value?.authenticate === true ?
                    <Route element={<ProtectedRoute />}>
                        <Route path={value.path} element={<Suspense>
                            <LazyComponent />
                        </Suspense> } />
                    </Route>
                    :

                    <Route path={value.path} element={<Suspense>
                        <LazyComponent />
                    </Suspense> } />
                }
            </Route>
        })}
        
    </Routes>
}

export default RouterProvider