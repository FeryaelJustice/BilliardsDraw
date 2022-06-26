package com.billiardsdraw.billiardsdraw.domain.map

import com.billiardsdraw.billiardsdraw.data.model.*;

// File that maps: Data model to Domain model

fun User.toUser() = com.billiardsdraw.billiardsdraw.domain.model.User(
    id = id,
    name = name,
    email = email,
    password = password,
    role = role
)