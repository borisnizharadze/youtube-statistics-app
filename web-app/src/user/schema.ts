import * as Yup from "yup"

export const loginSchema = Yup.object({
    username: Yup.string().required("Email is required"),
    password: Yup.string().min(4).max(16).required("Password is required")
})

export const signUpSchema = Yup.object({
    username: Yup.string().min(1).required("Username is required"),
    password: Yup.string().min(4).max(16).required("Password is required"),
    confirmPassword: Yup.string().oneOf([Yup.ref('password'), null], 'Passwords must match'),
    countryCode: Yup.string().min(2).max(2).required(),
    intervalMins: Yup.number().min(1).max(60).required()
})
