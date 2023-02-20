import type {AppProps} from 'next/app'
import {ToastContainer} from 'react-toastify'
import 'react-toastify/dist/ReactToastify.css'
import '../styles/global.css'
import {AuthContextProvider} from "../src/auth/AuthorizedContext"
import ThemeProvider from "@mui/system/ThemeProvider"
import {theme} from "../src/theme/theme"


function MyApp({Component, pageProps}: AppProps) {
    return (
        <ThemeProvider theme={theme}>
            <AuthContextProvider>
                <Component {...pageProps} />
                <ToastContainer
                    position="bottom-right"
                    autoClose={2000}
                    closeOnClick
                    hideProgressBar={false}
                    newestOnTop={false}
                    rtl={false}
                    pauseOnFocusLoss={false}
                    draggable={false}
                    pauseOnHover={false}
                />
            </AuthContextProvider>
        </ThemeProvider>
    )
}

export default MyApp
