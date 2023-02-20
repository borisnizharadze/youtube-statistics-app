import {FlexBox} from "../components/FlexBox"
import LoginForm from "./LoginForm"
import {useState} from "react"
import {useAuthContext} from "../auth/AuthorizedContext"
import {LoginValues} from "./props"
import styled from "@emotion/styled"
import LineSeparatorWithText from "../components/LineSeparatorWIthText"
import {Button, Grid} from "@mui/material"
import {theme} from "../theme/theme"
import {useRouter} from "next/router"

const RightPanel = styled.div`
    display: flex;
    align-items: center;
    text-align: center;
    justify-content: center;
    background-color: ${theme.palette.secondary.main};
    box-shadow: 0 2px 8px #8fa2f740;
    height: 100%;
    color: white;
    font-size: 50px;
    text-transform: uppercase;
    text-wrap: white-space;
`

const LoginTextContainer = styled.div`
    font-size: 20px;
    text-transform: uppercase;
    text-wrap: white-space
`

const LoginContainer = styled.div`
    display: flex;
    flex-direction: column;
    gap: 10px;
`

const LoginFormContainer = () => {
    const {login} = useAuthContext()
    const [submitDisabled, setSubmitDisabled] = useState(false)
    const router = useRouter()

    const handleLoginRequest = (values: LoginValues) => {
        return login(values, setSubmitDisabled)
    }

    const handleRedirectToSignUpPage = () => {
        router.push("/signup")
    }

    return (
        <Grid container spacing={2}>
            <Grid item md={5} xs={12} order={{xs: 2, md: 1}}>
                <FlexBox justifyContent={"center"}
                    alignItems={"center"}
                    height={"100%"}
                >
                    <LoginContainer>
                        <LoginTextContainer>{"Sign in"}</LoginTextContainer>
                        <LoginForm submitDisabled={submitDisabled} onLoginRequest={handleLoginRequest} />
                        <LineSeparatorWithText text={"Or"} />
                        <Button disabled={submitDisabled}
                            onClick={handleRedirectToSignUpPage}
                            variant={"contained"}
                            color={"secondary"}>{"Sing up"}</Button>
                    </LoginContainer>
                </FlexBox>
            </Grid>
            <Grid item md={7} xs={12} order={{xs: 1, md: 2}}>
                <RightPanel>{"FB Videos Statistics"}</RightPanel>
            </Grid>
        </Grid>
    )
}

export default LoginFormContainer