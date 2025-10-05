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

## TODO List

### Generic
[X] Add Kotlin checkstyle
[ ] Update Spring boot version
[ ] Add github actions to build on PR and on merge
[X] Move to Hexagonal
[ ] ArchUnit tests

### Authors
[X] Add integration tests
[ ] Add pagination, ordering and search in find endpoint
[ ] Add param in getById to retrieve details (books or readers) 

### Books
[ ] Add genre
[ ] Add endpoints to add books
[ ] Languages for the title

### Genre
[ ] Add CRUD for genre

### TBR
[ ] Add TBR list

### Reading
[ ] Add format and platform
[ ] Add crud
