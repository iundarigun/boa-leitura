import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import useToBeReadForm from "@/features/tbr/hooks/useToBeReadForm.js";
import ToBeReadForm from "@/features/tbr/components/ToBeReadForm.jsx";

export default function ToBeReadFormPage() {
  const {
    initialData,
    loading,
    saving,
    handleSubmit,
    handleCancel,
  } = useToBeReadForm();

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-3xl mx-auto p-8">
        <CardHeader>
          <CardTitle className="text-2xl">
            ✏️ Edit TBR Details
          </CardTitle>
        </CardHeader>
        <CardContent>
          {(loading || !initialData) ? (
            <p className="text-center text-gray-500">Loading ...</p>
          ) : (
            <>
            <ToBeReadForm
              key={initialData.id}
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
