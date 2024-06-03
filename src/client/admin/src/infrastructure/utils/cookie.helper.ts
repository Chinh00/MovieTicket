import Cookies from 'js-cookie'
import {AuthSuccessResponse} from "@/app/usecases/auth.usecase.ts";

class CookieHelper {
    public static SaveToken(data: AuthSuccessResponse) {
        Cookies.set("auth", JSON.stringify(data))
    }
    public static GetToken() {
        return JSON.parse(Cookies.get("auth") || "{}") as AuthSuccessResponse
    }
    public static ClearToken () {
        Cookies.remove("auth")
    }
    
}

export default CookieHelper