import styled from "@emotion/styled"

const Container = styled('div')({
    display: 'flex',
    alignItems: 'center',
    width: '100%'
})

const Border = styled('span')({
    borderBottom: '2px solid lightslategrey',
    width: '100%'
})

const Content = styled('span')({
    padding: '5px',
    fontWeight: '500',
    fontSize: '16px',
    color: 'black',
    whiteSpace: 'nowrap'
})

interface LineSeparatorWithTextProps {
    text: string
}

const LineSeparatorWithText = (props: LineSeparatorWithTextProps) => {

    return (
        <Container>
            <Border />
            <Content>{props.text}</Content>
            <Border />
        </Container>
    )
}

export default LineSeparatorWithText