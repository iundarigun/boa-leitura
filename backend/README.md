# Boa Leitura

This project aims to import and normalize data from csv file of goodreads.

### Current Version
This first version is just an import from csv. To use it, first must run the _docker-compose_:

```shell
docker-compose up -d
```

And run the project from any IDE or using gradle command:

```shell
./gradlew bootRun
```

And import using [swagger endpoint](http://localhost:1980/swagger-ui/index.html) or using curl:
```shell
curl -X 'POST' \
  'http://localhost:1980/imports' \
  -H 'accept: */*' \
  -H 'Content-Type: application/json' \
  -d /Users/uri/Downloads/goodreads_library_export_2024.csv
```

Every book, author and reading is imported just ones, even if the file is reimported or reprocessed. For book, is used the id in goodreads, for author is used the name (this must be improved) and for reading is used the book and the read date.

> the entries without data read will be ignored

### What is missing
- The original language of the book is not set automatically. 
- Also, the gender of the authors must be set manually.
- The re-read is not tested, so I don't know what happen if import a re-read. 

---

# TODO List

[ ] Organize README
[ ] Pull request template
[ ] Add github actions to build on PR and on merge

## Backend

### Generic
[X] Add Kotlin checkstyle
[X] Update Spring boot version
[X] Move to Hexagonal
[x] ArchUnit tests
[ ] Verify error message
[X] Use specifications for all finds
[ ] Dockerize app


### Authors
[X] Add integration tests
[X] Add pagination, ordering and search in find endpoint
[ ] Add param in getById to retrieve details (books or readers) 
[x] Show gender in PascalCase
[x] Show Nationality in a Select searchable

### Books
[X] Add genre
[X] Order by asc/desc
[X] Add endpoints to add books
[X] Languages for the title
[X] Job to retrieve images
[X] Search images by title
[X] Add language in the searchBy isbn/title
[X] Popup selection by title
[X] Search by keyword

### Sagas
[X] Crud Sagas
[X] Move status to out of Saga entity

### Genre
[X] Add CRUD for genre

### TBR
[X] Add TBR list

### Reading
[X] Add format and platform
[X] Add crud
[X] Change rating to double
[ ] Integration Tests

### Import
[X] Review import mechanism
[X] Adapt current importation
[ ] Create an import page

### Best of
[ ] Add mechanism to choose the best x year

### Statistics
[X] Year statistics: Number of books by year
[ ] Entering in the year, other statistics
   [X] Group by books, authors, languages
   [ ] sagas

## Frontend

### Generic
[X] Reorganize Frontend (pages, components)
[ ] Dockerize app

### Menu
[ ] Reorganize menu, add util menu

### Import
[ ] Add page to import goodreads csv
