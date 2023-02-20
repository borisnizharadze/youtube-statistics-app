import {FlexBox} from "../components/FlexBox"
import SignUpForm from "./SignUpForm"
import {SignUpValues} from "./props"
import {useState} from "react"
import {useAuthContext} from "../auth/AuthorizedContext"
import styled from "@emotion/styled"
import LineSeparatorWithText from "../components/LineSeparatorWIthText"
import {Button, Grid} from "@mui/material"
import {useRouter} from "next/router"
import {theme} from "../theme/theme"

const RightPanel = styled.div`
    display: flex;
    align-items: center;
    text-align: center;
    justify-content: center;
    background-color: ${theme.palette.secondary.main};
    box-shadow: 0 2px 8px #8fa2f740;
    color: white;
    height: 100%;
    font-size: 50px;
    text-transform: uppercase;
    text-wrap: white-space;
`

const SignupTextContainer = styled.div`
    font-size: 20px;
    text-transform: uppercase;
    text-wrap: white-space
`

const SignupContainer = styled.div`
    display: flex;
    flex-direction: column;
    gap: 10px;
`

const SignUpFormContainer = () => {
    const {signUp} = useAuthContext()
    const [submitDisabled, setSubmitDisabled] = useState(false)
    const router = useRouter()

    const handleSignUpRequest = async (values: SignUpValues) => {
        return signUp(values, setSubmitDisabled)
    }

    const handleRedirectToLoginPage = () => {
        router.push("/login")
    }

    return (
        <Grid container spacing={1}>
            <Grid item md={5} xs={12} order={{xs: 2, md: 1}}>
                <FlexBox justifyContent={"center"}
                    alignItems={"center"}
                    height={"100%"}
                >
                    <SignupContainer>
                        <SignupTextContainer>{"sign up"}</SignupTextContainer>
                        <SignUpForm submitDisabled={submitDisabled} onSignUpRequest={handleSignUpRequest} />
                        <LineSeparatorWithText text={"Or"} />
                        <Button disabled={submitDisabled}
                            onClick={handleRedirectToLoginPage}
                            variant={"contained"}
                            color={"secondary"}>{"Sing in"}</Button>
                    </SignupContainer>
                </FlexBox>
            </Grid>
            <Grid item md={7} xs={12} order={{xs: 1, md: 2}}>
                <RightPanel>{"FB Videos Statistics"}</RightPanel>
            </Grid>
        </Grid>
    )
}

export default SignUpFormContainer