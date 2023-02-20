import styled from "@emotion/styled"
import {FlexBox} from "./FlexBox"

const StyledDiv = styled(FlexBox)`
    position: absolute;
    left: 0;
    top: 0;
    z-index: 100;
    background-color: #ffffff;
`

const FullScreenLoading = () => {
    return (
        <StyledDiv height={"100vh"}
            width={"100vw"}
            justifyContent={"center"}
            alignItems={"center"}>
            <img style={{transition: "all .2s"}} src='/svg/loading.svg' alt='next' />
        </StyledDiv>
    )
}

export default FullScreenLoading