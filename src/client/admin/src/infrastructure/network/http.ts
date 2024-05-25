import axios, {AxiosInstance} from "axios";
import {AppConfig} from "@/core/config/AppConfig.ts";
import * as nProgress from "nprogress";

function sleep(ms = 500): Promise<void> {
    console.log('Kindly remember to remove `sleep`');
    return new Promise((resolve) => setTimeout(resolve, ms));
}

class Http {
    public instance: AxiosInstance
    
    constructor() {
        this.instance = axios.create({
            baseURL: AppConfig.BASE_URL,
            timeout: 2 * 60 * 60
        })
        this.instance.interceptors.request.use(value => {
            nProgress.start()
            return value
        }, error => {
            nProgress.done()
            return new Promise(error)
        })
        
        this.instance.interceptors.response.use( async value => {
            /*await sleep(2000)*/
            nProgress.done()
            return value
        }, error => {
            nProgress.done()
            return new Promise(error)
        })
    }
    
}

export default new Http().instance