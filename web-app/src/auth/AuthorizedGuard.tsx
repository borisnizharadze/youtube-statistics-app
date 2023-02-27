import {useRouter, NextRouter} from "next/router"
import {ReactNode, useEffect, useRef, useState} from "react"
import {useAuthContext} from "./AuthorizedContext"
import {AuthorizedState} from "./props"
import FullScreenLoading from "../components/FullScreenLoading"

const AuthorizedGuard = ({children}: { children: ReactNode }) => {
    const [childComponent, setChildComponent] = useState<ReactNode>(<FullScreenLoading />)
    const {authState} = useAuthContext()
    const router = useRouter()
    const routerRef = useRef<NextRouter>(router)

    useEffect(() => {
        if (authState !== AuthorizedState.AUTHORIZED) {
            routerRef.current.push('/login')
        } else if (authState === AuthorizedState.AUTHORIZED) {
            setChildComponent(children)
        }
    }, [authState, children])


    return (<>{childComponent}</>)
}

export default AuthorizedGuard