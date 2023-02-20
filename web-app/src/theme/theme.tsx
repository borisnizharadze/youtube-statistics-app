import {alpha, createTheme, darken, lighten} from "@mui/material/styles"

const colorOptions = {
    palette: {
        primary: {
            main: '#EBBB66',
            light: lighten('#EBBA66', 0.9),
            dark: darken('#EBBA66', 0.1),
            contrastText: '#11151e'
        },
        secondary: {
            main: '#11151e',
            light: lighten('#11151e', 0.9),
            dark: darken('#11151e', 0.1),
            contrastText: '#F9F9F9'
        },
        error: {
            main: '#d6433b',
            contrastText: '#F9F9F9'
        }
    }
}

export const theme = createTheme(colorOptions, {
    palette: {
        primary: {
            main: '#EBBB66',
            light: lighten('#EBBA66', 0.9),
            dark: darken('#EBBA66', 0.1),
            contrastText: '#11151e'
        },
        secondary: {
            main: '#11151e',
            light: lighten('#11151e', 0.9),
            dark: darken('#11151e', 0.1),
            contrastText: '#F9F9F9'
        },
        error: {
            main: '#d6433b',
            contrastText: '#F9F9F9'
        }
    },

    components: {
        MuiButton: {
            styleOverrides: {
                containedPrimary: {
                    backgroundColor: colorOptions.palette.primary.main,
                    boxShadow: colorOptions.palette.primary.dark + '  1px 1px',
                    color: colorOptions.palette.primary.contrastText,
                    ':hover': {
                        backgroundColor: alpha(colorOptions.palette.primary.main, 0.8),
                        boxShadow: 'none'
                    }
                },
                containedSecondary: {
                    backgroundColor: colorOptions.palette.secondary.main,
                    boxShadow: colorOptions.palette.secondary.dark + '  1px 1px',
                    color: colorOptions.palette.secondary.contrastText,
                    ':hover': {
                        backgroundColor: alpha(colorOptions.palette.secondary.main, 0.8),
                        boxShadow: 'none'
                    }
                },
            }
        },
        MuiInputBase: {
            styleOverrides: {
                root: {
                    width: '360px',
                    borderRadius: '8px',
                    backgroundColor: '#F9F9F9'
                }
            }
        }
    },
})