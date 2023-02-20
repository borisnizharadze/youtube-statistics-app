import {useForm} from "react-hook-form"
import {LoginFormProps, LoginValues} from "./props"
import {loginSchema} from "./schema"
import {yupResolver} from '@hookform/resolvers/yup'
import {Button, CircularProgress, TextField} from "@mui/material"
import {FlexBox} from "../components/FlexBox"

const LoginForm = (props: LoginFormProps) => {
    const {register, handleSubmit, formState: {errors}} = useForm<LoginValues>({
        resolver: yupResolver(loginSchema)
    })

    return (
        <form onSubmit={handleSubmit(props.onLoginRequest)}>
            <FlexBox flexDirection={"column"}
                gap={"10px"}
            >
                <TextField
                    {...register("username")}
                    label='Username'
                    name={"username"}
                    type={"text"}
                    error={!!errors.username}
                    helperText={!!errors.username ? <span>{errors.username.message}</span> : ""}
                />
                <TextField
                    {...register("password")}
                    label={'Password'}
                    name={"password"}
                    type={"password"}
                    error={!!errors.password}
                    helperText={!!errors.password ?
                        <span>{errors.password.message}</span> : ""}
                />
                <Button disabled={props.submitDisabled}
                    variant={"contained"}
                    type={"submit"}
                    value={props.submitDisabled ? "loading" : "submit"}
                >
                    Sign in {!!props.submitDisabled ? <>{'\u00A0'} <CircularProgress size={15} /></> : ""}
                </Button>
            </FlexBox>
        </form>
    )
}

export default LoginForm