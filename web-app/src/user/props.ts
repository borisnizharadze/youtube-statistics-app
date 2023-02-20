import {loginSchema, signUpSchema} from "./schema"
import * as Yup from "yup"

export type LoginValues = Yup.InferType<typeof loginSchema>

export interface LoginFormProps {
    onLoginRequest: (data: LoginValues) => Promise<void>
    submitDisabled?: boolean
}

export type SignUpValues = Yup.InferType<typeof signUpSchema>

export interface SignUpFormProps {
    onSignUpRequest: (data: SignUpValues) => Promise<void>
    submitDisabled?: boolean
}