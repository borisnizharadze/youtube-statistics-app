import {useRouter} from "next/router"
import {ReactNode, useEffect, useRef, useState} from "react"
import {useAuthContext,} from "./AuthorizedContext"
import {AuthorizedState} from "./props"
import FullScreenLoading from "../components/FullScreenLoading"
import {NextRouter} from "next/dist/client/router"

const UnauthorizedGuard = ({children}: { children: ReactNode }) => {
    const [childComponent, setChildComponent] = useState<ReactNode>(<FullScreenLoading />)
    const {authState} = useAuthContext()
    const router = useRouter()
    const routerRef = useRef<NextRouter>(router)

    useEffect(() => {
        if (authState !== AuthorizedState.UNAUTHORIZED) {
            routerRef.current.push('/')
        } else if (authState === AuthorizedState.UNAUTHORIZED) {
            setChildComponent(children)
        }
    }, [authState, children])

    return <>{childComponent}</>
}

export default UnauthorizedGuard