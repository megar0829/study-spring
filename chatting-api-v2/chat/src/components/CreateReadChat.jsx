import {useRef, useState} from "react";
import {Stomp} from "@stomp/stompjs";
import * as SockJs from "sockjs-client";
import * as StompJs from "@stomp/stompjs";

export default function CreateReadChat() {

    const client = useRef({});
    const [chat, setChat] = useState("");
    const [chatList, setChatList] = useState([]);
    // const socket = new SockJs("http//localhost:8080/ws-stomp")


    const connect = () => {
        client.current = new StompJs.Client({
            brokerURL: "ws://localhost:8080/ws-stomp",
            onConnect: () => {
                console.log("success");
                subscribe();
            }
        })
        client.current.activate();
    };

    const disconnect = () => {
        client.current.deactivate();
        console.log('채팅이 종료되었습니다.')
    }
    const subscribe = () => {
        client.current.subscribe(`/api/sub`, (body) => {
            setChatList((_chat_list) => [
                ..._chat_list, JSON.parse(body.body)
            ]);
        })
    }

    const publish = (chat) => {
        if (!client.current.connected) {
            return;
        }

        client.current.publish({
            destination: `/pub/chat`,
            body: JSON.stringify({
                roomId: 1,
                message: chat,
            }),
        });

        setChat("");
    }

    // 채팅 입력 시 Chat 값 변경
    const handleChange = (event) => {
        setChat(event.target.value);
    };

    // 보내기 버튼 눌렀을 때 publish
    const handleSubmit = (event, chat) => {
        event.preventDefault();

        publish(chat)
    }


    return (
        <div>
            <div className={"chat-list"}>{chatList}</div>
            <button onClick={() => connect()}>Connect</button>
            <button onClick={() => disconnect()}>Disconnect</button>

            <form onSubmit={(event) => handleSubmit(event, chat)}>
                <div>
                    <input type={"text"} name={"chatInput"} onChange={handleChange} value={chat} />
                </div>

                <input type={"submit"} value={"의견 보내기"} />
            </form>
        </div>
    );
}