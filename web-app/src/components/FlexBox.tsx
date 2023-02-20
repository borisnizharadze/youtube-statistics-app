import styled from '@emotion/styled'
import {HTMLAttributes} from "react"

interface FlexBoxProps extends HTMLAttributes<HTMLElement> {
    flexDirection?: string,
    justifyContent?: string;
    alignItems?: string;
    height?: string;
    width?: string;
    bgColor?: string;
    gap?: string;
    padding?: string;
    margin?: string;
}

export const FlexBox = styled.div<FlexBoxProps>`
    display: flex;
    flex-direction: ${props => props.flexDirection};
    justify-content: ${props => props.justifyContent};
    align-items: ${props => props.alignItems};
    height: ${props => props.height};
    width: ${props => props.width};
    background-color: ${props => props.bgColor};
    gap: ${props => props.gap};
    padding: ${props => props.padding};
    margin: ${props => props.margin};
`

