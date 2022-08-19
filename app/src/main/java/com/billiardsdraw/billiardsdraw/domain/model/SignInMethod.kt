package com.billiardsdraw.billiardsdraw.domain.model

sealed class SignInMethod(val method: String) {
    object Custom: SignInMethod("custom")
    object Google: SignInMethod("google")
}