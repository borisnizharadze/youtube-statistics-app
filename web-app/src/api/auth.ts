import {LoginValues, SignUpValues} from "../user/props"
import {backendAPI} from "./base"

interface AuthResponse {
    success: boolean,
    data?: Record<string, string>,
    message: string;
}

export const AuthApi = {
    login: async (params: LoginValues): Promise<AuthResponse> => {
        try {
            const loginResponse = await backendAPI.post('/auth/login', params)
            return {
                success: true,
                data: loginResponse.data,
                message: "Successful login"
            }
        } catch (err: any) {
            return {
                success: false,
                message: err.response?.data ?? err.message,
            }
        }
    },

    signUp: async (params: SignUpValues) => {
        try {
            const signUpResponse = await backendAPI.post('/auth/register', params)
            return {
                success: true,
                data: signUpResponse.data,
                message: "Successful signUp"
            }
        } catch (err: any) {
            return {
                success: false,
                message: err.response?.data ?? err.message,
            }
        }
    }
}
