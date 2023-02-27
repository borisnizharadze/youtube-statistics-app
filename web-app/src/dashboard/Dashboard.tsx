import styled from "@emotion/styled"
import { useEffect, useState } from "react"
import { Client } from "@stomp/stompjs"
import SockJS  from "sockjs-client"
import { useAuthContext } from "../auth/AuthorizedContext"
import { FlexBox } from "../components/FlexBox"


const ButtonContainer = styled.div`
    display: flex;
    flex-direction: row;
    gap: 1rem;
`

interface Statistics {
    videoLink: string,
    commentLink: string
}

const Dashboard = () => {
    const {token} = useAuthContext()
    const [statistics, setStatistics] = useState<Statistics |null>(null)

    useEffect(() => {
        if (token !== undefined) {
            const stompClient = new Client({
                connectHeaders: {
                    'x-access-token': token,
                },
                webSocketFactory: () => {
                    return new SockJS('http://localhost:8080/socket')
                }
            })
        
            stompClient.onConnect = (response, ) => {
                stompClient.subscribe('/user/topic/statistics-update', (message) => {
                    setStatistics(JSON.parse(message.body) as Statistics)
                })
                stompClient.publish({destination: "/app/connect"})
            }
    
            stompClient.activate()
            return () => {
                stompClient.unsubscribe('/user/topic/statistics-update')
                stompClient.deactivate()
                console.log('Disconnected from Stomp endpoint')
            }
        }
    }, [token])
      
    return (
        <FlexBox flexDirection="column">
            <div>{'video link:'} <a href={statistics?.videoLink}>{statistics?.videoLink}</a></div>
            <div>{'comment link:'} <a href={statistics?.commentLink}>{statistics?.commentLink}</a></div>
        </FlexBox>
    )
}

export default Dashboard