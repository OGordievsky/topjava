curl -X GET "http://localhost:8080/topjava/rest/meals" -H "accept: application/json" -D result.headers -o result.json

curl -X GET "http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=10:00&endDate=2020-01-31&endTime=20:00" -H "accept: application/json" -D result.headers -o result.json

curl -X GET "http://localhost:8080/topjava/rest/meals/100006" -D result.headers -o result.json

curl -d "{\"dateTime\":\"2022-01-28T14:30:00\", \"description\":\"New meal\", \"calories\":\"100\" }" -H "Content-Type: application/json" -X POST "http://localhost:8080/topjava/rest/meals" -D result.headers -o result.json

curl -d "{\"id\":100006,\"dateTime\":\"2020-01-31T00:00:00\",\"description\":\"EditMeal\",\"calories\":100,\"user\":null}" -H "Content-Type: application/json" -X PUT "http://localhost:8080/topjava/rest/meals/100006"

curl -X GET "http://localhost:8080/topjava/rest/meals/100006" -D result.headers -o result.json

curl -X DELETE "http://localhost:8080/topjava/rest/meals/100006"

curl -X GET "http://localhost:8080/topjava/rest/meals/100006" -D result.headers -o result.json