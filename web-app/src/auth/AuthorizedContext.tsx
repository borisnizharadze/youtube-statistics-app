import {createContext, ReactNode, useCallback, useContext, useEffect, useMemo, useRef} from "react"
import {useCookies} from "react-cookie"
import {AuthApi} from "../api/auth"
import {useRouter} from "next/router"
import {toast} from "react-toastify"
import {LoginValues, SignUpValues} from "../user/props"
import {AuthorizedContextType, AuthorizedState} from "./props"
import {AxiosError} from "axios"
import {backendAPI} from "../api/base"
import {NextRouter} from "next/dist/client/router"
import { User } from "../model/user"


export const AuthorizedContext = createContext<null | AuthorizedContextType>(null)

export const AuthContextProvider = (props: { children: ReactNode }) => {
    const [cookie, setCookie, removeCookie] = useCookies(['token', 'user'])
    const router = useRouter()
    const routerRef = useRef<NextRouter>(router)

    const logout = useCallback(() => {
        removeCookie('token')
        removeCookie('user')
        toast.dismiss()
        routerRef.current.push("/login")
    }, [removeCookie])

    const login = useCallback(async (values: LoginValues, setFetching: Function) => {
        setFetching(true)
        const resp = await AuthApi.login(values)
        setFetching(false)
        if (resp.success) {
            if (resp.data) {
                setCookie('token', resp.data.jwt)
                setCookie('user', resp.data.user)
                routerRef.current.push("/").then(() => {
                    toast.success(resp.message)
                })
            } else {
                removeCookie('token')
                removeCookie('user')
            }
        } else {
            toast.error(<span data-test={"form-error"}>{resp.message}</span>)
        }

    }, [setCookie, removeCookie])

    const signUp = useCallback(async (values: SignUpValues, setFetching: Function) => {
        setFetching(true)
        const resp = await AuthApi.signUp(values)
        setFetching(false)
        if (resp.success) {
            routerRef.current.push("/login").then(() => {
                toast.success(resp.message)
            })
        } else {
            toast.error(<span data-test={"form-error"}>{resp.message}</span>)
        }
    }, [])


    useEffect(() => {
        const reqInterceptor = (config: any) => {
            return {
                ...config,
                headers: {
                    ...config.headers,
                    "x-access-token":  cookie.token,
                }
            }
        }

        const resInterceptor = (response: any) => {
            return response
        }

        const errInterceptor = (error: AxiosError) => {
            if (error.response?.status === 401 || error.response?.status === 403) {
                logout()
            }
            if (error.code === AxiosError.ERR_NETWORK) {
                routerRef.current.push("/maintenance")
            }

            return Promise.reject(error)
        }

        const requestInterceptor = backendAPI.interceptors.request.use(reqInterceptor, errInterceptor)

        const responseInterceptor = backendAPI.interceptors.response.use(resInterceptor, errInterceptor)

        return () => {
            backendAPI.interceptors.request.eject(requestInterceptor)
            backendAPI.interceptors.response.eject(responseInterceptor)
        }

    }, [logout, cookie.token])


    const contextValue = useMemo(() => ({
        authState: cookie.token ? AuthorizedState.AUTHORIZED : AuthorizedState.UNAUTHORIZED,
        login,
        logout,
        signUp,
        token: cookie.token,
        user: cookie.user
    }), [cookie.token, cookie.user, login, logout, signUp])

    return (
        <AuthorizedContext.Provider value={contextValue}>
            {props.children}
        </AuthorizedContext.Provider>
    )
}

export const useAuthContext = () => {
    const ctx = useContext(AuthorizedContext)
    if (ctx === null) {
        throw new Error(`You can't access authContext outside AuthProvider`)
    }
    return ctx
}
