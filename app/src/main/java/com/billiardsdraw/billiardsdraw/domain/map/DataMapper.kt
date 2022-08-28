package com.billiardsdraw.billiardsdraw.domain.map

import com.billiardsdraw.billiardsdraw.data.model.user.User
import com.google.firebase.auth.FirebaseUser
import java.util.*

// File that maps: Data model to Domain model

fun User.toUser() = com.billiardsdraw.billiardsdraw.domain.model.User(
    id = id,
    uid = uid,
    username = username,
    nickname = nickname,
    name = name,
    surnames = surnames,
    email = email,
    password = password,
    age = age,
    birthdate = birthdate,
    country = country,
    carambola_paints = carambola_paints,
    pool_paints = pool_paints,
    role = role,
    active = active,
    deleted = deleted
)

fun FirebaseUser.toUser() = com.billiardsdraw.billiardsdraw.domain.model.User(
    id = null,
    uid = uid,
    username = "username",
    nickname = "nickname",
    name = "name",
    surnames = "surnames",
    email = email!!,
    password = "password",
    age = "18",
    birthdate = Date(),
    country = "Spain",
    carambola_paints = arrayOf(),
    pool_paints = arrayOf(),
    role = "free",
    active = true,
    deleted = true
)