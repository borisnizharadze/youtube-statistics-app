import { SettingsValues } from "./props"
import SettingsForm from "./SettingsForm"
import {useCallback, useState} from "react"
import { backendAPI } from "../api/base"
import { SettingsApi } from "../api/settings"
import { toast } from "react-toastify"

const SettingsFormContainer = () => {
    const [submitDisabled, setSubmitDisabled] = useState(false)

    const handleSettingsUpdate = (values: SettingsValues) => {
        setSubmitDisabled(true)
        return updateSettings(values)
    }

    const updateSettings = useCallback(async (values: SettingsValues) => {
        setSubmitDisabled(true)
        const resp = await SettingsApi.updateSettings(values)
        setSubmitDisabled(false)
        if (resp.success) {
            console.log(resp.message)
            toast.success(<span>{resp.message}</span>)
        } else {
            toast.error(<span data-test={"form-error"}>{resp.message}</span>)
        }

    }, [])


    return <SettingsForm onSettingsUpdate={handleSettingsUpdate} submitDisabled={submitDisabled} />
} 

export default SettingsFormContainer