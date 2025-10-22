import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import ReadingForm from "@/features/reading/components/ReadingForm";
import useReadingForm from "@/features/reading/hooks/useReadingForm.js";

const API_URL = "/readings";

export default function ReadingFormPage() {
  const {
    isEdit,
    initialData,
    loading,
    saving,
    handleSubmit,
    handleCancel,
  } = useReadingForm();

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-3xl mx-auto p-8">
        <CardHeader>
          <CardTitle className="text-2xl">
            {isEdit ? "✏️ Edit Reading" : "➕ New Reading"}
          </CardTitle>
        </CardHeader>
        <CardContent>
          {(loading || !initialData) ? (
            <p className="text-center text-gray-500">Loading reading...</p>
          ) : (
            <>
            <ReadingForm
              key={initialData.id || initialData.book?.id}
              onSubmit={handleSubmit}
              editingReading={initialData}
              onCancel={handleCancel}
              loading={saving}
            />
          </>
          )}
        </CardContent>
      </Card>
    </div>
  );
}
