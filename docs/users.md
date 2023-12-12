
## Register

Endpoint : POST /api/users

Request Body :
````json
{
  "username" : "maman",
  "nip" : "123",
  "password" : "rahasia"
}
````

Response Body(Success) :

````json
{
  "Data": "OK"
}
````
Response Body(Failed) :

```json
{
  "Data": "KO",
  "erros": "username must not blank"
}
```

## Login

Endpoint : POST /api/auth/login

Request Body :
````json
{
  "nip" : "123",
  "password" : "rahasia"
}
````

Response Body(Success) :

````json
{
  "Data": {
    "token": "TOKEN",
    "expiredAt": ""
  }
}
````
Response Body(Failed) :

```json
{
  "Data": "KO",
  "erros": "username must not blank"
}
```

