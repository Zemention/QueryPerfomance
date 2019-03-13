java 9 is required

This service allows us to compare performance of different sql queries against various databases.
and benchmark the performance of different queries versions.

API example:

POST http://{{host}}:8080/query_perfomance

request:
```json
{
  "queries":[
        "select * from news",
        "select * from author"
    ]
 }
 ```
 response
```json
[
    {
        "result": [
            {
                "query": "Select * From Products;",
                "resultTest": 26706664
            },
            {
                "query": "Select * From Products Where name = produc;",
                "resultTest": 0
            }
        ],
        "errorMessage": null,
        "dataBaseName": "postgresql"
    },
    {
        "result": [
            {
                "query": "Select * From Products;",
                "resultTest": 31520135
            },
            {
                "query": "Select * From Products Where name = produc;",
                "resultTest": 0
            }
        ],
        "errorMessage": null,
        "dataBaseName": "mysql"
    }
]
 ```
or you can choose DataBases for which you want fire performance tests for example:
```json
{
    "queries": [
        "Select * From Products;",
        "Select * From Products Where name = produc;"

    ],
    "database": [
    	"postgresql"]
}
 ```
Developer guide to plug in a new database:

added to application.yml into db:
                                dataBaseInfo:
 info about dataBase for Example

     -
       name: 'name of data base'
       url: 'jdbc:postgresql://192.168.99.100:5432/queryPerformance'
       user: 'user name'
       password: 'user password'


