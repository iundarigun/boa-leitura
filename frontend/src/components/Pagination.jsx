import { Button } from "@/components/ui/button";

export default function Pagination({ page, setPage, totalPages}) {

  return (
    <div className="flex items-center justify-center space-x-2">
      <Button
        variant="outline"
        onClick={() => setPage((p) => Math.max(p - 1, 1))}
        disabled={page <= 1}
      >
        Previous
      </Button>

      <span>
        Page {page} of {totalPages}
      </span>

      <Button
        variant="outline"
        onClick={() => setPage((p) => Math.min(p + 1, totalPages))}
        disabled={page >= totalPages}
      >
        Next
      </Button>
    </div>
  );
}