import { User } from "../model/user"
import {LoginValues, SignUpValues} from "../user/props"

export const enum AuthorizedState {
    AUTHORIZED = 'AUTHORIZED',
    UNAUTHORIZED = 'UNAUTHORIZED'
}

export interface AuthorizedContextType {
    authState: AuthorizedState,
    login: (values: LoginValues, setFetching: Function) => Promise<void>
    signUp: (values: SignUpValues, setFetching: Function) => Promise<void>
    logout: () => void,
    token: string,
    user: User
}