import {Input} from "@/components/ui/input.jsx";
import {useState} from "react";
import {Button} from "@/components/ui/button.jsx";

export default function ChangePasswordForm({saving, onSubmit}) {
  const [oldPassword, setOldPassword] = useState("");
  const [password, setPassword] = useState("");
  const [repeatPassword, setRepeatPassword] = useState("");

  const handleCancel = () => {
    setOldPassword("");
    setPassword("");
    setRepeatPassword("");
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    if (!oldPassword || !oldPassword.trim()) {
      onSubmit(null, {validationError: "Current Password is required."});
      return;
    }
    if (!password || !password.trim()) {
      onSubmit(null, {validationError: "New password is required."});
      return;
    }
    if (!repeatPassword || !repeatPassword.trim()) {
      onSubmit(null, {validationError: "Repeat Password is required."});
      return;
    }

    if (repeatPassword !== password) {
      onSubmit(null, {validationError: "The password does not match."});
      return;
    }
    onSubmit({
      oldPassword,
      password,
      repeatPassword
    });
    handleCancel();
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <label className="block text-sm font-medium mb-1">Current Password</label>
        <Input
          type="password"
          value={oldPassword}
          onChange={(e) => setOldPassword(e.target.value)}
          placeholder="Enter Old password"
          required
        />
      </div>
      <div>
        <label className="block text-sm font-medium mb-1">New Password</label>
        <Input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Enter new password"
          required
        />
      </div>
      <div>
        <label className="block text-sm font-medium mb-1">Repeat Password</label>
        <Input
          type="password"
          value={repeatPassword}
          onChange={(e) => setRepeatPassword(e.target.value)}
          placeholder="Repeat new password"
          required
        />
      </div>
      <div className="flex gap-2">
        <Button type="submit" disabled={saving}>
          {saving ? "Saving..." : "Change Password"}
        </Button>
        <Button type="button" variant="outline" onClick={handleCancel}>
          Cancel
        </Button>
      </div>
    </form>
  );
}