import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import {User} from 'lucide-react';
import {useAuth} from "@/context/AuthContext.jsx";
import useChangePassword from "@/features/user/hooks/useChangePassword.js";
import ChangePasswordForm from "@/features/user/components/ChangePasswordForm.jsx";

export default function UserPreferencesPage() {
  const {user} = useAuth();
  const {
    saving,
    handleSubmit
  } = useChangePassword();

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-3xl mx-auto p-8">
        <CardHeader>
          <CardTitle className="text-2xl flex gap-4">
            <User/>{user?.sub}
          </CardTitle>
        </CardHeader>
        <CardContent>
          <ChangePasswordForm
            saving={saving}
            onSubmit={handleSubmit}/>
        </CardContent>
      </Card>
    </div>
  );
}
