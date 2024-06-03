import http from "@/infrastructure/network/http.ts";
import {useMutation} from "react-query";
import {AppConfig} from "@/core/config/AppConfig.ts";
import {useAppDispatch} from "@/app/stores/hook.ts";
import {ChangeAuthenticate} from "@/app/stores/app/AppSlice.ts";
import CookieHelper from "@/infrastructure/utils/cookie.helper.ts";
import {useNavigate} from "react-router-dom";



export type UserLoginModel = {
    username: string,
    password: string
}

export const GetToken = async (data: UserLoginModel) => {
    return await http.post<AuthSuccessResponse>(`/admin-identity/connect/token`, new URLSearchParams({
        grant_type: 'password',
        client_id: 'react_client',
        username: data.username,
        password: data.password,
        scope: "openid profile api"
    }), {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
    });
}


export const RevocationToken = async () => {
    const token: AuthSuccessResponse = CookieHelper.GetToken()
    return await http.post<AuthSuccessResponse>(`/admin-identity/connect/revocation`, new URLSearchParams({
        token: token.access_token,
        token_type_hint: "access_token",
        client_id: 'react_client',
    }), {
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded'
        },
    });
}

export type AuthSuccessResponse = {
    access_token: string,
    expires_in: number,
    token_type: string,
    scope: string
}

const useLoginDefault = () => {
    const dispatch = useAppDispatch()
    const nav = useNavigate()
    return useMutation({
        mutationKey: ["login-default"],
        mutationFn: GetToken,
        onSuccess: (data, variables) => {
            dispatch(ChangeAuthenticate(true))
            nav(-1)
        }
    })
}

const useLogoutDefault = () => {
    return useMutation({
        mutationKey: "logout",
        mutationFn: RevocationToken,
        onSuccess: () => {
            CookieHelper.ClearToken()
        },
        onError: () => {
            CookieHelper.ClearToken()
        }
    })
}

export {useLoginDefault, useLogoutDefault}