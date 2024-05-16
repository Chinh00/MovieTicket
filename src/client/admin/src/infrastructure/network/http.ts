import axios, {AxiosInstance} from "axios";
import {AppConfig} from "@/core/config/AppConfig.ts";

class Http {
    public instance: AxiosInstance
    
    constructor() {
        this.instance = axios.create({
            baseURL: AppConfig.BASE_URL,
            timeout: 2 * 60
        })
        this.instance.interceptors.request.use(value => {
            return value
        }, error => {
            return new Promise(error)
        })
        
        this.instance.interceptors.response.use(value => {
            return value
        }, error => {
            return new Promise(error)
        })
    }
    
}

export default new Http().instance