import { SettingsValues } from "./props"
import SettingsForm from "./SettingsForm"
import {useCallback, useState} from "react"
import { backendAPI } from "../api/base"
import { SettingsApi } from "../api/settings"
import { toast } from "react-toastify"
import { useAuthContext } from "../auth/AuthorizedContext"
import {useCookies} from "react-cookie"
import { User } from "../model/user"

const SettingsFormContainer = () => {
    const [cookie, setCookie] = useCookies(['user'])
    const [submitDisabled, setSubmitDisabled] = useState(false)
    const {user} = useAuthContext()

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
            setCookie('user', {
                                ...user, 
                                interval: values.intervalMins, 
                                countryCode: values.countryCode
                            })
            toast.success(<span>{resp.message}</span>)
        } else {
            toast.error(<span data-test={"form-error"}>{resp.message}</span>)
        }

    }, [])


    console.log(user)

    return <SettingsForm values={cookie?.user} 
                        onSettingsUpdate={handleSettingsUpdate} 
                        submitDisabled={submitDisabled} />
} 

export default SettingsFormContainer