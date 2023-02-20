import * as Yup from "yup"

export const settingsSchema = Yup.object({
    countryCode: Yup.string().min(2).max(2).required(),
    intervalMins: Yup.number().min(1).max(60).required()
})