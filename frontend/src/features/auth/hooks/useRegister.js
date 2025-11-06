import {doRegister} from "@/lib/api/auth.js";
import {useDialog} from "@/context/DialogContext.jsx";
import {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import {useAuth} from "@/context/AuthContext.jsx";

export default function useRegister() {
  const [username, setUsername] = useState("");
  const [password, setPassword] = useState("");
  const [repeatPassword, setRepeatPassword] = useState("");
  const navigate = useNavigate();
  const { token } = useAuth();

  const { showError, showSuccess } = useDialog();

  useEffect(() => {
    if (token) navigate("/");
  }, [token, navigate]);

  const handleRegister = async (e) => {
    e.preventDefault();

      const {error} = await doRegister({ username, password, repeatPassword });

      if (error) {
        showError(error);
      } else {
        navigate("/login")
        showSuccess("User register successfully!")
      }

  };

  return {
    username,
    setUsername,
    password,
    setPassword,
    repeatPassword,
    setRepeatPassword,
    handleRegister
  }
}