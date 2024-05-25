import http from "@/infrastructure/network/http.ts";
import {useMutation} from "react-query";
import {AppConfig} from "@/core/config/AppConfig.ts";



export type UserLoginModel = {
    username: string,
    password: string
}

export const GetToken = async (data: UserLoginModel) => {
    const response = await http.post(`${AppConfig.AUTH_URL}/connect/token`, new URLSearchParams({
        grant_type: 'password',
        client_id: 'react_client',
        username: data.username,
        password: data.password,
        scope: "openid profile api"
    }), {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        }
    });
}

const useLoginDefault = () => {
    return useMutation({
        mutationKey: ["login-default"],
        mutationFn: GetToken
    })
}
export {useLoginDefault}