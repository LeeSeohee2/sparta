import { Link, useNavigate } from "react-router-dom";
import { useState } from "react";
import { loginApi } from "../api/authApi";

export default function LoginPages() {
  const navigate = useNavigate();
  const [email, setEmail] = useState("");
  const [password, setPassword] = useState("");

  async function handleSubmit(e) {
    e.preventDefault();
    try{
      //성공시 실행하는 명령문들 작성한다.
      const login = {
        email : email,
        password : password
      }

      const res = await loginApi(login);
      console.log(res);

      // 로컬스토리지에 저장한다.
      localStorage.setItem("accessToken",res.token);
      localStorage.setItem("userId" , res.userId);

      alert("로그인이 정상적으로 되었습니다. 채팅방 목록으로 갑니다.");
      navigate("/rooms");

    }catch(err){
      alert("로그인 실패함:" + err);
    }
  }

  return (
    <div className="flex justify-center items-center min-h-screen bg-gray-100">
      <form
        onSubmit={handleSubmit}
        className="bg-white shadow-lg p-8 rounded-2xl w-80 space-y-4"
      >
        <h1 className="text-center text-2xl font-bold">로그인</h1>

        <input
          className="border px-3 py-2 w-full rounded"
          placeholder="이메일"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
        />

        <input
          className="border px-3 py-2 w-full rounded"
          placeholder="비밀번호"
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button className="w-full bg-blue-600 text-white py-2 rounded-xl hover:bg-blue-700">
          로그인
        </button>

        <Link className="block text-center text-sm text-blue-600" to="/signup">
          회원가입
        </Link>
      </form>
    </div>
  );
}
