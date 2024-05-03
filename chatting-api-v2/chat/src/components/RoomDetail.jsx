import { useParams } from "react-router-dom";
import {useEffect, useState} from "react";
import axios from "axios";
import * as SockJs from "sockjs-client";
import * as StompJS from "@stomp/stompjs";
import {Stomp} from "@stomp/stompjs";

export default function RoomDetail (props) {

  const sock = new SockJs("/api/ws-stomp");
  const ws = Stomp.over(sock);

  const id = props.id;

  const {roomId} = useParams();

  const [message, setMessage] = useState("");

  const [messageList, setMessageList] = useState([]);

  const [room, setRoom] = useState({});

  useEffect(() => {
    console.log("detail", props.id);

    connect();

  }, []);

  const findRoom = async () => {
    await axios.get(`/api/chat/room/${id}`)
      .then((res) => {
        console.log("room Data: ", res.data);
        setRoom(res.data);
      })
  };

  const sendMessage = () => {
    ws.send("/api/pub/chat/message", {}, JSON.stringify({
      messageType: "TALK",
      roomId: id,
      sender: "나",
      message: message
    }));

    setMessage("");
  }

  const getMessage = (gm) => {
    setMessageList(gm);
  }

  const connect = async () => {
    ws.connect({}, function (frame) {
      ws.subscribe("/api/sub/chat/room" + id, function (message) {
        const data = JSON.parse(message);
        getMessage(data);
      });
      ws.send("/api/pub/chat/message", {}, JSON.stringify({
        messageType: "ENTER",
        roomId: id,
        sender: "나"
      }))
    })

    await findRoom();
  }

  return (
    <div className="container">
      <h2>{roomId} 방</h2>
      { room &&
        <>
          <div>
            <h2>{room.name}</h2>
          </div>

          <div className="input-group">
            <div>
              <label className="input-group-text">내용</label>
            </div>

            <input onChange={(e) => setMessage(e.target.value)} value={message} type="text" className="form-control"/>

            <div className="input-group-append">
              <button onClick={() => sendMessage()} className="btn btn-primary">보내기</button>
            </div>
          </div>
          <ul className="list-group">
            {messageList.length > 0 && messageList.map((msg, index) => {
              return (
                <li className="list-group-item" key={index}>
                  {msg.sender} - {msg.message}
                </li>
              );
            })}
          </ul>
        </>
      }
    </div>
  );
}

