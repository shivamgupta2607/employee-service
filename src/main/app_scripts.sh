## Start local database
docker run --rm --name employee_db -d -p 5433:5432 -e POSTGRES_USER=employee -e POSTGRES_PASSWORD=employee -e POSTGRES_DB=employee_db postgres:9.6