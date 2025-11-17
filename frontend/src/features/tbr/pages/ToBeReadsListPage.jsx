import {Button} from "@/components/ui/button";
import {Input} from "@/components/ui/input";
import {Card, CardHeader, CardTitle} from "@/components/ui/card";
import Pagination from "@/components/Pagination";
import useToBeReads from "@/features/tbr/hooks/useToBeReads.js";
import ToBeReadTable from "@/features/tbr/components/ToBeReadTable.jsx";
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue} from "@/components/ui/select.jsx";
import TagsInputWithSuggestions from "@/components/TagsInputWithSuggestions.jsx";
import {READING_PLATFORMS} from "@/lib/platform.js";
import {LANGUAGES} from "@/lib/languages.js";

export default function ToBeReadsListPage() {
  const {
    toBeReads,
    loading,
    page,
    setPage,
    totalPages,
    filterKeyword,
    setFilterKeyword,
    filterBought,
    setFilterBought,
    filterTags,
    setFilterTags,
    sortField,
    sortDir,
    handleSearch,
    handleDelete,
    handleSort,
    handleEdit,
    handleDragEnd,
    handleMarkAsDone,
    handleMarkAsBought
  } = useToBeReads();

  const tagSuggestions = READING_PLATFORMS.map((item) => item.label)
    .concat(LANGUAGES.map((item) => item.label));

  return (
    <div className="min-h-screen bg-gray-50 p-6 flex justify-center">
      <Card className="w-full max-w-6xl mx-auto p-8">
        <CardHeader className="flex flex-row items-center justify-between">
          <CardTitle className="text-3xl">📖 To Be Read</CardTitle>
        </CardHeader>
        <div className="flex flex-wrap gap-3 items-center">
          <Input
            placeholder="Search by keyword..."
            value={filterKeyword}
            onChange={(e) => setFilterKeyword(e.target.value)}
            onKeyDown={(e) => e.key === "Enter" && handleSearch()}
            className="w-64"
          />
          <Select value={filterBought} onValueChange={setFilterBought}>
            <SelectTrigger className="w-40">
              <SelectValue placeholder="Filter by bought"/>
            </SelectTrigger>
            <SelectContent>
              <SelectItem value="both">Both</SelectItem>
              <SelectItem value="bought">Bought</SelectItem>
              <SelectItem value="non-bought">Non-bought</SelectItem>
            </SelectContent>
          </Select>
            <TagsInputWithSuggestions
              value={filterTags}
              onChange={setFilterTags}
              suggestions={tagSuggestions}
              placeholder="Search by any tag..."
            />
          <Button onClick={handleSearch}>Search</Button>
        </div>

        <Pagination
          page={page}
          setPage={setPage}
          totalPages={totalPages}
        />

        <ToBeReadTable
          toBeReads={toBeReads}
          loading={loading}
          onEdit={handleEdit}
          onDelete={handleDelete}
          sortField={sortField}
          sortDir={sortDir}
          onSort={handleSort}
          onDragEnd={handleDragEnd}
          onMarkAsDone={handleMarkAsDone}
          onMarkAsBought={handleMarkAsBought}
        />

        <Pagination
          page={page}
          setPage={setPage}
          totalPages={totalPages}
        />
      </Card>
    </div>
  );
}
