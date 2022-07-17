package com.billiardsdraw.billiardsdraw.data.model

enum class HttpResponseCode(val code: Int) {
    OK(200),
    WARNING(300),
    ERROR_CLIENT(400),
    ERROR_CLIENT_NOTFOUND(404),
    ERROR_SERVER(500)
}