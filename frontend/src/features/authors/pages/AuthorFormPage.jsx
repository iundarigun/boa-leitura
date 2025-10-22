import { Card, CardHeader, CardTitle, CardContent } from "@/components/ui/card";
import AuthorForm from "@/features/authors/components/AuthorForm";
import useAuthorForm from "@/features/authors/hooks/useAuthorForm.js";

export default function AuthorFormPage() {
  const {
    isEdit,
    initialData,
    loading,
    saving,
    handleSubmit,
    handleCancel,
  } = useAuthorForm();

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-lg mx-auto p-8">
        <CardHeader>
          <CardTitle className="text-2xl">
            {isEdit ? "✏️ Edit Author" : "➕ New Author"}
          </CardTitle>
        </CardHeader>
        <CardContent>
          {loading ? (
            <p className="text-center text-gray-500">Loading author...</p>
          ) : (
          <AuthorForm
            onSubmit={handleSubmit}
            editingAuthor={initialData}
            onCancel={handleCancel}
            loading={saving}
          />
          )}
        </CardContent>
      </Card>
    </div>
  );
}
