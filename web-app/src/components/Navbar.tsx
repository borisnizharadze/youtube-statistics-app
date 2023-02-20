import {FlexBox} from "./FlexBox"
import {ReactNode} from "react"
import {useRouter} from "next/router"
import {theme} from "../theme/theme"
import styled from "@emotion/styled"
import {Button} from "@mui/material"
import {useAuthContext} from "../auth/AuthorizedContext"

interface Page {
    route: string,
    label: string,
}

const pages: Page[] = [
    {
        route: "/",
        label: "Dashboard"
    },
    {
        route: "/settings",
        label: "Settings"
    },
]

const StyledDiv = styled.div`
    color: ${theme.palette.primary.contrastText};
    &:hover {
        color: ${theme.palette.secondary.contrastText};
    }
`
const PageButton = (props: {
    page: Page
}) => {
    const router = useRouter()

    const handleClick = () => {
        router.push(props.page.route)
    }

    return (
        <StyledDiv data-active={router.pathname === props.page.route} onClick={handleClick}>
            {props.page.label}
        </StyledDiv>
    )

}


const Navbar = (props: { children: ReactNode }) => {
    const {logout} = useAuthContext()

    const handleLogout = () => {
        logout()
    }

    return (
        <FlexBox
            flexDirection={"column"}
            width={"100%"}
        >
            <FlexBox
                height={"50px"}
                bgColor={theme.palette.primary.main}
                padding={"10px"}
                justifyContent={"space-between"}
            >
                <FlexBox
                    justifyContent={"center"}
                    alignItems={"center"}
                    gap={"20px"}
                    data-test={"nav-bar"}
                >
                    {
                        pages.map((page) => <PageButton key={page.label} page={page} />)
                    }
                </FlexBox>
                <Button variant={"contained"} color={"secondary"} onClick={handleLogout}>{"logout"}</Button>
            </FlexBox>

            <div style={{
                height: "calc(100vh - 50px)",
                backgroundColor: theme.palette.primary.light
            }}>
                <div style={{margin: "20px"}}>
                    {props.children}
                </div>
            </div>
        </FlexBox>
    )

}

export default Navbar