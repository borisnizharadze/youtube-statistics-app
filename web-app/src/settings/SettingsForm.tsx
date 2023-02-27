import {useForm} from "react-hook-form"
import {SettingsFormProps, SettingsValues} from "./props"
import {yupResolver} from '@hookform/resolvers/yup'
import {Button, CircularProgress, TextField} from "@mui/material"
import {FlexBox} from "../components/FlexBox"
import { settingsSchema } from "./schema"

const SettingsForm = (props: SettingsFormProps) => {
    const {register, handleSubmit, formState: {errors}} = useForm<SettingsValues>({
        resolver: yupResolver(settingsSchema),
        defaultValues: props?.values
    })

    return (
        <form onSubmit={handleSubmit(props.onSettingsUpdate)}>
            <FlexBox flexDirection={"column"}
                gap={"10px"}
            >
                <TextField
                    {...register("intervalMins")}
                    label='Interval (mins)'
                    name={"intervalMins"}
                    type={"number"}
                    error={!!errors.intervalMins}
                    helperText={!!errors.intervalMins ? <span>{errors.intervalMins.message}</span> : ""}
                />
                <TextField
                    {...register("countryCode")}
                    label={'Country code'}
                    name={"countryCode"}
                    type={"text"}
                    error={!!errors.countryCode}
                    helperText={!!errors.countryCode ?
                        <span>{errors.countryCode.message}</span> : ""}
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

export default SettingsForm