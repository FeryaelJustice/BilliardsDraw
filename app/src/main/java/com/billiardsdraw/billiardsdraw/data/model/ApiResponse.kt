package com.billiardsdraw.billiardsdraw.data.model

data class ApiResponse(var id: Int? = null, val users: List<User>, val errorCode: HttpResponseCode)
