import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import {Button} from "@/components/ui/button";
import {Loader2, Upload} from "lucide-react";
import useImports from "@/features/user/hooks/useImports.js";

export default function ImportGoodreadsPage() {
  const {
    file,
    loading,
    handleSubmit,
    handleFileChange
  } = useImports();

  return (
    <div className="flex items-center justify-center min-h-screen bg-muted/20 p-6">
      <Card className="max-w-md w-full shadow-lg">
        <CardHeader>
          <CardTitle className="text-center text-xl font-bold">
            Import from Goodreads
          </CardTitle>
        </CardHeader>

        <CardContent className="flex flex-col items-center space-y-4">
          <input
            type="file"
            accept=".csv"
            onChange={handleFileChange}
            className="block w-full text-sm text-gray-700 file:mr-4 file:py-2 file:px-4
              file:rounded-full file:border-0 file:text-sm file:font-semibold
              file:bg-primary file:text-primary-foreground hover:file:bg-primary/80"
          />

          {file && (
            <p className="text-sm text-gray-600">
              Selected file: <strong>{file?.name}</strong>
            </p>
          )}

          <Button
            onClick={handleSubmit}
            disabled={loading}
            className="w-full"
          >
            {loading ? (
              <>
                <Loader2 className="mr-2 h-4 w-4 animate-spin"/>
                Sending...
              </>
            ) : (
              <>
                <Upload className="mr-2 h-4 w-4"/>
                Upload file
              </>
            )}
          </Button>
        </CardContent>
      </Card>
    </div>
  );
}
