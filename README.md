# Anular SSO Apps
Anular SSO Apps is an application program that provide a single sign on for a user to registered site in Anular SSO Group.

### API Documentation
API divides by two category, NonSign in API and Sign in API
* __NonSign in API__ use to a user that didn't provide a sign in before use signin
* __Sign in API__ use to a user that must signin first before use this API
#### NonSign in API
###### User Registration
* you can register a user by using this url
```
https://anular-sso-api.herokuapp.com/api/user/register
```
user must provide the data like in this below
```
{
    "fullName": "UserTest",
    "userName": "usertest",
    "password": "usertest",
    "email": "user@mail.com",
    "gender": "male or female",
    "phoneNumber": "082345678",
    "agid": "WklvLvI-G4CCMXGzzr8LlWklvLv6"
}
```
Note: the agid data was a group that the user authenticate by. so in the real case you would signin in the site that use Anular SSO Apps

* the program will return the json in the below
```
{
    "id": "6klvLvI-WklvLvI-z4TCysc3QaP6X",
    "fullName": "UserTest",
    "userName": "usertest",
    "password": "$2a$10$aEJvk/cgmvvVMLTebMaI0.16oR7mjWpgkL4JzGRqtEZTOT7g5PSe6",
    "email": "user@mail.com",
    "gender": "male or female",
    "phoneNumber": "0812345678",
    "anularUserStat": "unverified",
    "agid": "WklvLvI-G4CCMXGzzr8LlWklvLv6"
}
```
Note: you must fulfill all the data first and you can't register using already exist username they will give you this error message
```
{
    "timestamp": 1637123473838,
    "status": 403,
    "error": "Forbidden",
    "message": "username already exist",
    "path": "/api/user/register"
}
```
* when you register you will get an email by the program that provide an OTP Link to use your account to sign in
###### user SignIn
* you can signIn by this url
```
https://anular-sso-api.herokuapp.com/api/user/signin
```
you must provide json data like this below
```
{
    "userName": "UserTest",
    "password": "user"
}
```
and the program will give you this json data
```
{
    "stat": "success",
    "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJBZG1pbjEiLCJpYXQiOjE2MzcxMjM4NjQsImV4cCI6MTYzNzEyNzQ2NH0.oUCbXM_YB93t6w5CITFGLUpEIfsE3gyCfbEpBZyX7XtijTfwrZNVwwX6aV6muxmqs4PJrZle9PGxaT70crM0oA"
}
```
* you can use this token to authenticate the SignIn API. the token is JWT based 
if your username didn't exist they will return you this error
```
{
    "timestamp": 1637123904873,
    "status": 403,
    "error": "Forbidden",
    "message": "username is empty",
    "path": "/api/user/signin"
}
```
if your password wrong they will give you
```
{
    "timestamp": 1637124031904,
    "status": 401,
    "error": "Unauthorized",
    "message": "Unauthorized",
    "path": "/api/user/signin"
}
```
if your email isn't verification first you will get this json
```
{
    "timestamp": 1637124691686,
    "status": 400,
    "error": "Bad Request",
    "message": "you must verif your email first",
    "path": "/api/user/signin"
}
```

###### Group Registration
* you can create get your site by adding this url in anular SSO Apps
```
https://anular-sso-api.herokuapp.com/api/group/request
```
the json you must provide must like this
```
{
    "groupName": "Enigma Bank",
    "ast" : "t3YynGa3ELfJY3UNcCTJLjnzrVK",
    "userId": "WklvLvI-WklvLv-lzJEqzj9eQ8m2"
}
```
Note: the ast is a site type that can provide and fetch while you creating a group

* they will give this return
```
{
    "id": "WklvLvI-G4CCMXGzzr8LlWklvLv6",
    "groupName": "Enigma Bank",
    "ast": "t3YynGa3ELfJY3UNcCTJLjnzrVK"
}
```

#### SignIn API
* you must bring the request by providing header by bearer API
###### Get User
* you can get user by this link url
```
https://anular-sso-api.herokuapp.com/api/user?id=<id>
```
the <id> must provide the user id
* the return you will get like this
```
{
    "id": "XklvLvI-WklvLvI-tefvJoMXkxrGf",
    "fullName": "UserTest",
    "userName": "UserTest",
    "password": "$2a$10$LYaQDyHuDagDOmhDn4jFOuFlbaI7TSoIvo4TFXoL4Wo0lRdaYNx8G",
    "email": "test@mail.com",
    "gender": "male or female",
    "phoneNumber": "0812345678",
    "anularUserStat": "unverified",
    "agid": "WklvLvI-G4CCMXGzzr8LlWklvLv6"
}
```
if the id didn't exist
```
{
    "timestamp": 1637125366877,
    "status": 400,
    "error": "Bad Request",
    "message": "user id isn't exist",
    "path": "/api/user"
}
```
###### Update User
* the link you must use is like this below
```
https://anular-sso-api.herokuapp.com/api/user
```
when sending an update you must provide json like this below
```
{
    "id": "XklvLvI-WklvLvI-tefvJoMXkxrGf",
    "fullName": "UserTest",
    "userName": "UserTest",
    "password": "$2a$10$LYaQDyHuDagDOmhDn4jFOuFlbaI7TSoIvo4TFXoL4Wo0lRdaYNx8G",
    "email": "test@mail.com",
    "gender": "male or female",
    "phoneNumber": "0812345678",
    "anularUserStat": "unverified",
    "agid": "WklvLvI-G4CCMXGzzr8LlWklvLv6"
}
```
Note: the id must have the already exist account
* the program will give this json
```
{
    "id": "XklvLvI-WklvLvI-tefvJoMXkxrGf",
    "fullName": "UserTest",
    "userName": "UserTest",
    "password": "$2a$10$LYaQDyHuDagDOmhDn4jFOuFlbaI7TSoIvo4TFXoL4Wo0lRdaYNx8G",
    "email": "test@mail.com",
    "gender": "male or female",
    "phoneNumber": "0812345678",
    "anularUserStat": "unverified",
    "agid": "WklvLvI-G4CCMXGzzr8LlWklvLv6"
}
```
if the id isn't exist they will give you
```
{
    "timestamp": 1637125366877,
    "status": 400,
    "error": "Bad Request",
    "message": "user id isn't exist",
    "path": "/api/user"
}
```