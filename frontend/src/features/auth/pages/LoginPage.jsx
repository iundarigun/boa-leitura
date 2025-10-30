import {Input} from "@/components/ui/input";
import {Button} from "@/components/ui/button";
import useLogin from "@/features/auth/hooks/useLogin.js";

export default function LoginPage() {
  const {
    username,
    setUsername,
    password,
    setPassword,
    handleLogin
  } = useLogin();


  return (
    <div className="flex min-h-screen items-center justify-center bg-gray-100">
      <form
        onSubmit={handleLogin}
        className="bg-white p-8 rounded-xl shadow-md w-96 space-y-4"
      >
        <h2 className="text-2xl font-bold text-center">Login</h2>
        <Input
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          required
        />
        <Input
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          required
        />
        <Button className="w-full" type="submit">
          Sign in
        </Button>
      </form>
    </div>
  );
}
