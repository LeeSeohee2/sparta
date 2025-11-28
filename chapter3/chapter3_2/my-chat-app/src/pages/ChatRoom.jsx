import { useEffect, useRef, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";

export default function ChatRoom() {
  const navigate = useNavigate();
  const { roomId } = useParams();

  const userId = localStorage.getItem("userId");
  if (!userId) navigate("/");

  const socketRef = useRef(null);
  const [messages, setMessages] = useState([]);
  const [input, setInput] = useState("");

  useEffect(() => {
    const socket = new WebSocket("ws://localhost:8080/ws/chat");
    socketRef.current = socket;

    socket.onopen = () => {
      console.log("웹소켓 연결 성공!");

      socket.send(
        JSON.stringify({
          type: "ENTER",
          roomId,
          userId,
          content: `${userId}번 유저가 입장했습니다.`,
        })
      );
    };

    //메시지 수신 설정 (반드시 useEffect 내부)
    socket.onmessage = (event) => {
      console.log("수신:", event.data);
      const msg = JSON.parse(event.data);
      setMessages((prev) => [...prev, msg]);
    };

    return () => socket.close();
  }, [roomId, userId]);

  const handleSend = () => {
    if (!input.trim()) return;

    const msg = {
      type: "CHAT",
      roomId,
      userId,
      content: input,
    };

    socketRef.current.send(JSON.stringify(msg));
    setInput("");
  };

  return (
    <div className="min-h-screen bg-[#e5e9ef] flex justify-center pt-20">
      <div className="w-[600px] h-[470px] bg-white rounded-xl shadow-xl flex flex-col">

        <div className="bg-[#1f3c6b] text-white px-4 py-3 rounded-t-xl flex items-center justify-between">
          <button onClick={() => navigate("/rooms")} className="text-xl mr-3">
            ←
          </button>

          <div className="flex items-center gap-2 flex-1">
            <span className="font-semibold">💬 ChatOn</span>
          </div>
        </div>

        <div className="flex-1 overflow-y-auto px-4 py-3 space-y-3">
          <p className="text-gray-500 text-center mb-2">
            ★ {roomId}번 방 / {userId}번님 입장하였습니다.
          </p>

          {messages.map((m, i) => (
            <div key={i}>
              <b>{m.userId}번:</b> {m.content}
            </div>
          ))}
        </div>

        <div className="border-t p-4 flex items-center">
          <input
            value={input}
            onChange={(e) => setInput(e.target.value)}
            className="flex-1 border rounded-lg px-4 py-2 text-sm shadow-sm focus:outline-blue-400"
            placeholder="메시지 입력"
          />

          <button
            onClick={handleSend}
            className="ml-3 bg-blue-500 text-white w-10 h-10 rounded-full flex items-center justify-center hover:bg-blue-600"
          >
            전송
          </button>
        </div>
      </div>
    </div>
  );
}
