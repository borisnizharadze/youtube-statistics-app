import {NextPage} from "next/types"
import LoginFormContainer from "../src/user/LoginFormContainer"
import UnauthorizedGuard from "../src/auth/UnauthorizedGuard"

const Login: NextPage = () => {

    return (
        <UnauthorizedGuard>
            <LoginFormContainer />
        </UnauthorizedGuard>
    )
}

export default Login