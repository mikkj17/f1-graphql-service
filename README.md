# f1-graphql-service
A GraphQL API for querying Formula One data, implemented as a [Ktor](https://ktor.io/) server using the [Ktor Server Plugin by Expedia](https://opensource.expediagroup.com/graphql-kotlin/docs/server/ktor-server/ktor-overview). This API is a wrapper of https://github.com/jolpica/jolpica-f1.
## Running the application
To build the project, use the following command:
```
./gradlew build
```
If your build is successful, you can run it using the following command:
```
./gradlew run
```
Once the application is running, you can access a graphical user interface to query the API at http://0.0.0.0:8080/graphiql.

