import { NextPage } from "next"
import AuthorizedGuard from "../src/auth/AuthorizedGuard"
import Navbar from "../src/components/Navbar"
import SettingsFormContainer from "../src/settings/SettingsFormContainer"

const Settings: NextPage = () => (
    <AuthorizedGuard>
        <Navbar>
            <SettingsFormContainer />
        </Navbar>
    </AuthorizedGuard>
)

export default Settings
