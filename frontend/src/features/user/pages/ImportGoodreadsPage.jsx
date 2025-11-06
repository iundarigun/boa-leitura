import {Card, CardContent, CardHeader, CardTitle} from "@/components/ui/card";
import {Button} from "@/components/ui/button";
import {Loader2, Upload} from "lucide-react";
import useImports from "@/features/user/hooks/useImports.js";
import {Checkbox} from "@/components/ui/checkbox.jsx";

export default function ImportGoodreadsPage() {
  const {
    file,
    read,
    setRead,
    tbr,
    setTbr,
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

        <CardContent className="flex flex-col space-y-4">
          <div className="flex-1 space-x-2">
            <Checkbox
              id="read"
              checked={read}
              onCheckedChange={(checked) => setRead(!!checked)}
            />
            <label htmlFor="read" className="text-sm font-medium">
              Reads
            </label>
          </div>
          <div className="flex-1 space-x-2">
            <Checkbox
              id="tbr"
              checked={tbr}
              onCheckedChange={(checked) => setTbr(!!checked)}
            />
            <label htmlFor="tbr" className="text-sm font-medium">
              TBR
            </label>
          </div>
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
