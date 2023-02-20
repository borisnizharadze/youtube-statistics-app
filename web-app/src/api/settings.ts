import { SettingsValues } from "../settings/props"
import { backendAPI } from "./base"

interface SettingsResponse {
    success: boolean,
    data?: Record<string, string>,
    message: string;
}

export const SettingsApi = {
    updateSettings: async (params: SettingsValues): Promise<SettingsResponse> => {
        try {
            const settingsResponse = await backendAPI.put('/apiç/user', params)
            return {
                success: true,
                data: settingsResponse.data,
                message: "Successful login"
            }
        } catch (err: any) {
            console.log(err)
            return {
                success: false,
                message: err.message,
            }
        }
    }
}