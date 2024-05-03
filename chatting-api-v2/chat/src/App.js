import CreateReadChat from "./components/CreateReadChat";
import { BrowserRouter, Routes, Route } from "react-router-dom";
import RoomDetail from "./components/RoomDetail";
import {useEffect, useState} from "react";

function App() {
  const [id, setId] = useState("");

  const changeId = (text) => {
    setId(text);
  }

  useEffect(() => {
    console.log(id)
  }, [id]);

  return (
    <div className="p-5">
      <BrowserRouter>
        <Routes>
          <Route path="/" element={<CreateReadChat changeId={changeId} />}></Route>
          <Route path="/:roomId" element={<RoomDetail id={id} />}></Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}

export default App;
