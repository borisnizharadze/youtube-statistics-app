import {NextPage} from "next/types"
import {FlexBox} from "../src/components/FlexBox"
import {WarningAmberRounded} from '@mui/icons-material'
import {theme} from "../src/theme/theme"
import styled from "@emotion/styled"

const SmallTextContainer = styled.span`
    font-size: 20px;
    color: ${theme.palette.error.contrastText};
`

const LargeTextContainer = styled.span`
    font-size: 60px;
    color: ${theme.palette.error.contrastText};
`

const ExtraLargeTextContainer = styled.span`
    font-size: 80px;
    color: ${theme.palette.error.contrastText};
`

const Maintenance: NextPage = () => {

    return (
        <FlexBox justifyContent={"center"}
            alignItems={"center"}
            flexDirection={"column"}
            bgColor={theme.palette.error.main}
            color={theme.palette.error.contrastText}
            width={"100vw"}
            gap={"20px"}
        >
            <ExtraLargeTextContainer><WarningAmberRounded fontSize={"inherit"}
                color={"inherit"} /></ExtraLargeTextContainer>
            <LargeTextContainer>{"We’ll be back soon!"}</LargeTextContainer>
            <SmallTextContainer>{"Sorry for the inconvenience. We’re performing some maintenance at the moment"}</SmallTextContainer>
            <SmallTextContainer>{"— The Invoicer Team"}</SmallTextContainer>
        </FlexBox>
    )
}

export default Maintenance