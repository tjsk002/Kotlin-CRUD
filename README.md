## 환경

#### 사용스택

JDK21, Springboot, Kotlin, Spring-Security, Postgresql, Validation

### 유저 게시판 기능 구현

> 이전 리액트 프로젝트 기준으로 코틀린으로 CRUD 기능 구현

#### [API]

> User & Authentication API Documentation

## 1. User Management

### 1.1 Get User Details

Endpoint: GET /users

Request Body:

```
{
    "username": "acro1@acrofuture.com"
}
```

Response:

```
{
    "resultCode": "A200",
    "resultMessage": "Success",
    "resultData": {
        "message": "User details retrieved successfully",
        "user": {
            "username": "acro1@acrofuture.com",
            "nickName": "aa",
            "gender": "female",
            "isActive": true,
            "type": "back",
            "description": "this is description1"
        }
    }
}
```

### 1.2 Create User

Endpoint: POST /users/create

Request Body:

```
{
    "username": "acro1@acrofuture.com",
    "nickName": "aa",
    "gender": "female",
    "isActive": true,
    "type": "back",
    "description": "this is description1"
}
```

Response:

```
{
    "resultCode": "A200",
    "resultMessage": "Success",
    "resultData": {
        "message": "User create successful",
        "user": {
            "username": "acro1@acrofuture.com",
            "nickName": "aa",
            "gender": "female",
            "isActive": true,
            "type": "back",
            "description": "this is description1"
        }
    }
}
```

### 1.3 Edit User

Endpoint: PUT /users/edit

Request Body:

```
{
    "username": "acro1@acrofuture.com",
    "nickName": "aa",
    "gender": "female",
    "isActive": true,
    "type": "back",
    "description": "this is updated description"
}
```

Response:

```
{
    "resultCode": "A200",
    "resultMessage": "Success",
    "resultData": {
        "message": "User edit successful",
        "user": {
            "username": "acro1@acrofuture.com",
            "nickName": "aa",
            "gender": "female",
            "isActive": true,
            "type": "back",
            "description": "this is updated description"
        }
    }
}
```

### 1.4 Delete User (Soft Delete)

Endpoint: DELETE /users/delete

Request Body:

```
{
    "username": "acro1@acrofuture.com"
}
```

Response:

```
{
    "resultCode": "A200",
    "resultMessage": "Success",
    "resultData": {
        "message": "User delete successful"
    }
}
```

### 1.5 Get User List

Endpoint: GET /users/list

Response:

```
{
    "resultCode": "A200",
    "resultMessage": "Success",
    "resultData": {
        "content": [
            {
                "username": "acro1@acrofuture.com",
                "nickName": "aa",
                "gender": "female",
                "isActive": true,
                "type": "back",
                "description": "this is description1"
            }
        ],
        "pageInfo": {
                "pageNumber": 1,
                "pageSize": 10,
                "totalElements": 32,
                "totalPages": 4
            }
        }
    }
```

## 2. Authentication Management

### 2.1 Admin Signup

Endpoint: POST /auth/signup

Request Body:

```
{
    "username": "acro_admin1",
    "nickName": "acro",
    "password": "password123",
}
```

Response (Success):

```
{
    "resultCode": "A200",
    "resultMessage": "Success",
    "resultData": {
        "message": "Admin successfully created"
    }
}
```

Response (Bad Request): <이미있는 유저네임으로 사용자 생성 시>

```
{
    "resultCode": "A400",
    "resultMessage": "Bad Request",
    "resultData": {
        "message": "Username already exists"
    }
}
```

Response (Bad Request): <잘못된 형식 시>

```
{
    "resultCode": "A400",
    "resultMessage": "Bad Request",
    "resultData": {
        "message": "Failed to create user",
        "errors": {
            "nickName": "닉네임은 특수문자를 제외한 5~12자리여야 합니다.",
            "username": "아이디는 5~12자 이여야 합니다.",
            "password": "비밀번호는 필수 입력 값입니다."
        }
    }
}
```

### 2.2 Admin Login

Endpoint: POST /auth/login

Request Body:

```
{
    "username": "acro1",
    "password": "password123"
}
```

Response (Success):

Headers

```
Authorization: Bearer {Token}
```

Body

```
{
    "resultCode": "A200",
    "resultMessage": "Success",
    "resultData": {
        "message": "Login successful"
    }
}
```

Response (Unauthorized): <username 존재하지 않음>

```
{
    "resultCode": "A401",
    "resultMessage": "Unauthorized",
    "resultData": {
        "message": "Username not found"
    }
}
```

Response (Bad Request): <비밀번호가 틀림>

```
{
    "resultCode": "A400",
    "resultMessage": "Bad Request",
    "resultData": {
        "message": "Incorrect password"
    }
}
```

Response (Unauthorized): <잘못된 형식 시>

```
{
    "resultCode": "A400",
    "resultMessage": "Bad Request",
    "resultData": {
        "message": "Login failed due to server error",
        "errors": {
            "username": "아이디는 필수 입력 값입니다.",
            "password": "비밀번호는 필수 입력 값입니다."
        }
    }
}
```

### 2.3 Admin Logout

Endpoint: POST /auth/logout

Request Header:

```
Authorization: Bearer { jwt_token }
```

Response (Success):

```
{
    "resultCode": "A200",
    "resultMessage": "Success",
    "resultData": {
        "message": "Logout successful",
        "errors": null
    }
}
```

Response (Bad Request): <토큰 정보 입력하지 않을 때>

```
{
    "resultCode": "A401",
    "resultMessage": "Unauthorized",
    "resultData": {
        "message": "No authentication information",
        "errors": null
    }
}
```

Response (Bad Request): <이미 로그아웃 되어 있음>

```
{
    "resultCode": "A401",
    "resultMessage": "Unauthorized",
    "resultData": {
        "message": "Already logged out",
        "errors": null
    }
}
```

Response (Bad Request): <로그아웃 실패 (토큰이 없음 ex. 만료, 잘못된 토큰)>

```
{
    "resultCode": "A401",
    "resultMessage": "Unauthorized",
    "resultData": {
        "message": "Invalid token",
        "errors": null
    }
}
```

#### [DB]

```
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(255) UNIQUE NOT NULL,
    nick_name VARCHAR(255) NOT NULL,
    gender VARCHAR(10) CHECK (gender IN ('male', 'female')),
    is_active BOOLEAN DEFAULT TRUE,
    type VARCHAR(50) CHECK (type IN ('back', 'front', 'dba', 'infra')),
    password VARCHAR(255) NOT NULL,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    deleted_at TIMESTAMP
);
```

```
CREATE TABLE admins (
    id SERIAL PRIMARY KEY,
    user_name VARCHAR(100) UNIQUE NOT NULL,
    nick_name VARCHAR(100) NOT NULL,
    role VARCHAR(50) NOT NULL,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
    deleted_at TIMESTAMP
);
```

```
CREATE TABLE auth_tokens (
    id SERIAL PRIMARY KEY,
    user_id INT NOT NULL,
    token TEXT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expires_at TIMESTAMP NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);
```