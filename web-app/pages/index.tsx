import type {NextPage} from 'next'
import AuthorizedGuard from "../src/auth/AuthorizedGuard"
import Navbar from "../src/components/Navbar"
import Dashboard from '../src/dashboard/Dashboard'

const Home: NextPage = () => (
    <AuthorizedGuard>
        <Navbar>
            <Dashboard />
        </Navbar>
    </AuthorizedGuard>
)

export default Home
