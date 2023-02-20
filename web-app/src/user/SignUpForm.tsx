import {useForm} from "react-hook-form"
import {SignUpFormProps, SignUpValues} from "./props"
import {signUpSchema} from "./schema"
import {yupResolver} from '@hookform/resolvers/yup'
import {Button, CircularProgress, TextField} from "@mui/material"
import {FlexBox} from "../components/FlexBox"

const SignUpForm = (props: SignUpFormProps) => {

    const {register, handleSubmit, formState: {errors}} = useForm<SignUpValues>({
        resolver: yupResolver(signUpSchema)
    })

    return (
        <form onSubmit={handleSubmit(props.onSignUpRequest)}>
            <FlexBox flexDirection={"column"}
                gap={"10px"}
            >
                <TextField
                    {...register("username")}
                    label={"Username"}
                    name={"username"}
                    type={"text"}
                    error={!!errors.username}
                    helperText={!!errors.username ? <span>{errors.username.message}</span> : ""}
                />
                <TextField
                    {...register("password")}
                    label={"Password"}
                    name={"password"}
                    type={"password"}
                    error={!!errors.password}
                    helperText={!!errors.password ?
                        <span>{errors.password.message}</span> : ""}
                />
                <TextField
                    {...register("confirmPassword")}
                    label={"Confirm password"}
                    name={"confirmPassword"}
                    type={"password"}
                    error={!!errors.confirmPassword}
                    helperText={!!errors.confirmPassword ?
                        <span>{errors.confirmPassword.message}</span> : ""}
                />
                <TextField
                    {...register("countryCode")}
                    label={"Country code"}
                    name={"countryCode"}
                    type={"text"}
                    error={!!errors.countryCode}
                    helperText={!!errors.countryCode ?
                        <span>{errors.countryCode.message}</span> : ""}
                />
                <TextField
                    {...register("intervalMins")}
                    label={"Interval (minutes)"}
                    name={"intervalMins"}
                    type={"number"}
                    error={!!errors.intervalMins}
                    helperText={!!errors.intervalMins ?
                        <span>{errors.intervalMins.message}</span> : ""}
                />
                <Button disabled={props.submitDisabled}
                    variant={"contained"}
                    type={"submit"}
                    value={props.submitDisabled ? "loading" : "submit"}
                >
                    Sign up {!!props.submitDisabled ? <>{'\u00A0'} <CircularProgress size={15} /></> : ""}
                </Button>
            </FlexBox>
        </form>
    )
}

export default SignUpForm