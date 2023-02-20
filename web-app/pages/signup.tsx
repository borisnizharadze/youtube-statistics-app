import {NextPage} from "next/types"
import SignUpFormContainer from "../src/user/SignUpFormContainer"
import UnauthorizedGuard from "../src/auth/UnauthorizedGuard"

const Register: NextPage = () => {

    return (
        <UnauthorizedGuard>
            <SignUpFormContainer />
        </UnauthorizedGuard>
    )
}

export default Register