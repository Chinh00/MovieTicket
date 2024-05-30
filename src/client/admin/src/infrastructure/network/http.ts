import axios, {AxiosInstance} from "axios";
import {AppConfig} from "@/core/config/AppConfig.ts";
import * as nProgress from "nprogress";
import {AuthSuccessResponse} from "@/app/usecases/auth.usecase.ts";
import CookieHelper from "@/infrastructure/utils/cookie.helper.ts";

function sleep(ms = 500): Promise<void> {
    console.log('Kindly remember to remove `sleep`');
    return new Promise((resolve) => setTimeout(resolve, ms));
}    

class Http {
    public instance: AxiosInstance
    public accessToken: string | undefined
    constructor() {
        this.instance = axios.create({
            baseURL: AppConfig.BASE_URL,
            timeout: 2 * 60 * 60
        })
        this.accessToken = CookieHelper.GetToken().access_token
        this.instance.interceptors.request.use(value => {
            nProgress.start()
            
            if (this.accessToken) {
                value.headers.Authorization = `Bearer ${this.accessToken}`
            }
            
            return value
        }, error => {
            nProgress.done()
            return new Promise(error)
        })
        
        this.instance.interceptors.response.use( async value => {
            /*await sleep(2000)*/
            nProgress.done()
            if (value?.config.url === "/admin-identity/connect/token") {
                CookieHelper.SaveToken(value.data as AuthSuccessResponse)
                this.accessToken = (value.data as AuthSuccessResponse).access_token
            }
            return value
        }, error => {
            nProgress.done()
            return new Promise(error)
        })
    }
    
}

export default new Http().instance