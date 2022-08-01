package com.billiardsdraw.billiardsdraw.data.model.network

/***
 * GENERIC Network Response class
 */
sealed class NetworkResponse<T>(
    var data: T? = null,
    var message: String? = null,
    var user: com.billiardsdraw.billiardsdraw.domain.model.User? = null
) {
    class Success<T>(data: T, user: com.billiardsdraw.billiardsdraw.domain.model.User?) :
        NetworkResponse<T>(data, null, user)

    class Error<T>(
        message: String?,
        data: T? = null,
        user: com.billiardsdraw.billiardsdraw.domain.model.User?
    ) :
        NetworkResponse<T>(data, message, user)
}