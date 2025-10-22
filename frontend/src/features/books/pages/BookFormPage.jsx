import {Card, CardHeader, CardTitle, CardContent} from "@/components/ui/card";
import BookForm from "@/features/books/components/BookForm";
import useBookForm from "@/features/books/hooks/useBookForm.js";

export default function BookFormPage() {
  const {
    isEdit,
    initialData,
    loading,
    saving,
    handleSubmit,
    handleCancel,
  } = useBookForm();

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-3xl mx-auto p-8">
        <CardHeader>
          <CardTitle className="text-2xl">
            {isEdit ? "✏️ Edit Book" : "➕ New Book"}</CardTitle>
        </CardHeader>
        <CardContent>
          {loading ? (
            <p className="text-center text-gray-500">Loading book...</p>
          ) : (
            <BookForm
              onSubmit={handleSubmit}
              editingBook={initialData}
              onCancel={handleCancel}
              loading={saving}
            />
          )}
        </CardContent>
      </Card>
    </div>
  );
}
