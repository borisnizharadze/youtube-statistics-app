import * as Yup from "yup"
import { settingsSchema } from "./schema"


export type SettingsValues = Yup.InferType<typeof settingsSchema>

export interface SettingsFormProps {
    onSettingsUpdate: (data: SettingsValues) => Promise<void>
    submitDisabled?: boolean
}
