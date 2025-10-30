import {doLogin} from "@/lib/api/auth.js";
import {useDialog} from "@/context/DialogContext.jsx";
import {useState} from "react";
import {useNavigate} from "react-router-dom";
import {useAuth} from "@/context/AuthContext.jsx";

export default function useLogin() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const navigate = useNavigate();
  const { login } = useAuth();

  const { showError } = useDialog();

  const handleLogin = async (e) => {
    e.preventDefault();

    try {
      const {data, error} = await doLogin({ username, password });

      if (data) {
        login(data);
        navigate("/");
      } else {
        showError(error);
      }
    } catch (err) {
      console.error(err);
      showError("Invalid username or password");
    }
  };

  return {
    username,
    setUsername,
    password,
    setPassword,
    handleLogin
  }
}