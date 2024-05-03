import {useEffect, useState} from "react";
import axios from "axios";
import {Link} from "react-router-dom";

export default function CreateReadChat({changeId}) {


  const [roomName, setRoomName] = useState("");
  const [chatRoomList, setChatRoomList] = useState([]);

  useEffect(() => {
    getRooms();
  }, []);

    const getRooms = () => {
        axios.get("/api/chat/rooms")
            .then((res) => {
                setChatRoomList(res.data);
            })
    }

    const createRoom = (name) => {
        if (name == "") {
            alert("방 제목을 입력해 주세요");
            return;
        }

        const param = new URLSearchParams();
        param.append("name", name);

        axios.post("/api/chat/room", param)
            .then((res) => {
                alert(res.data.name + "방 개설에 성공하였습니다.");
                setRoomName("");
                getRooms();
            })
            .catch((err) => {
                alert("채팅방 개설에 실패하였습니다.");
            })
    }

    return (
        <>
            <div className="row">
                <div className="col-md-12">
                    <h3>채팅방 리스트</h3>
                </div>
            </div>

            <div className="input-group">
                <div className="input-group-prepend">
                    <label className="input-group-text">방제목</label>
                </div>
                <input onChange={(e) => setRoomName(e.target.value)} type="text" className="form-control" value={roomName} />
                <div className="input-group-append">
                    <button onClick={() => createRoom(roomName)} className="btn btn-primary" type="button">채팅방 개설</button>
                </div>
            </div>

            <ul className="list-group">
                {chatRoomList.length > 0 && chatRoomList.map((chatRoom, index) => {
                    return (
                        <li
                            key={index}
                            className="list-group-item list-group-item-action"
                        >
                            <Link onClick={() => changeId(chatRoom.id)} to={`/${index}`}>{chatRoom.name}</Link>
                        </li>
                    );
                })}
            </ul>
        </>
    );
}