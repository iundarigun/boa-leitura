import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import GenreForm from "@/features/genres/components/GenreForm";
import useGenreForm from "@/features/genres/hooks/useGenreForm.js";

export default function GenreFormPage() {
  const {
    isEdit,
    initialData,
    allGenres,
    loading,
    saving,
    handleSubmit,
    handleCancel,
  } = useGenreForm()
  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-2xl mx-auto p-8">
        <CardHeader>
          <CardTitle className="text-2xl">
            {isEdit ? "✏️ Edit Genre" : "➕ New Genre"}
          </CardTitle>
        </CardHeader>
        <CardContent>
          {loading ? (
            <p className="text-center text-gray-500">Loading author...</p>
          ) : (
            <GenreForm
              onSubmit={handleSubmit}
              editingGenre={initialData}
              onCancel={handleCancel}
              allGenres={allGenres}
              loading={saving}
            />
          )}
        </CardContent>
      </Card>
    </div>
  );
}
