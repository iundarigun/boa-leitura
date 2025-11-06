import {Input} from "@/components/ui/input";
import {Button} from "@/components/ui/button";
import useRegister from "@/features/auth/hooks/useRegister.js";

export default function RegisterPage() {
  const {
    username,
    setUsername,
    password,
    setPassword,
    repeatPassword,
    setRepeatPassword,
    handleRegister
  } = useRegister();


  return (
    <div className="flex min-h-screen items-center justify-center bg-gray-100">
      <form
        onSubmit={handleRegister}
        className="bg-white p-8 rounded-xl shadow-md w-96 space-y-4"
      >
        <h2 className="text-2xl font-bold text-center">Register</h2>
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
        <Input
          type="password"
          placeholder="Repeat password"
          value={repeatPassword}
          onChange={(e) => setRepeatPassword(e.target.value)}
          required
        />
        <div className="flex gap-4">
          <Button className="w-full" type="submit">
            Register
          </Button>
        </div>
      </form>
    </div>
  );
}
