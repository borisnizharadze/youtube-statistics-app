import {
    Autocomplete as MuiAutocomplete,
    CircularProgress as MuiCircularProgress,
    TextField as MuiTextField
} from "@mui/material"
import {ReactNode, SyntheticEvent} from "react"

export interface Option {
    key: string,
    label: string
}

const Autocomplete = (props: {
    onChange: (value: Option) => void,
    error: boolean,
    helperText: ReactNode,
    options: readonly Option[],
    loading: boolean,
    label: string,
    value?: Option,
    disabled?: boolean
    textFieldAttributes?: Record<string, string>
}) => {

    const handleSelect = (event: SyntheticEvent, option: Option | null) => {
        event.stopPropagation()
        if (option != null) {
            props.onChange(option)
        }
    }

    return (
        <>
            <MuiAutocomplete
                disabled={props.disabled}
                isOptionEqualToValue={(option: Option, value: Option) => option.key === value.key}
                getOptionLabel={(option: Option) => option.label}
                options={props.options}
                renderOption={(props, option: Option) => (
                    <li {...props} key={option.key}>{option.label}</li>
                )}
                value={props?.value ?? null}
                onChange={handleSelect}
                renderInput={(params) => (
                    <MuiTextField
                        {...params}
                        label={props.label}
                        error={props.error}
                        helperText={props.helperText}
                        InputProps={{
                            ...props.textFieldAttributes,
                            ...params.InputProps,
                            endAdornment: (
                                <>
                                    {props.loading ? <MuiCircularProgress size={20} /> : ''}
                                    {params.InputProps.endAdornment}
                                </>
                            ),
                        }}
                    />
                )}
            />
        </>
    )
}

export default Autocomplete