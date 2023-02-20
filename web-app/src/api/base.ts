import axios from "axios"

export const backendAPI = axios.create({
    headers: {'Content-Type': 'application/json'}
})