package com.billiardsdraw.billiardsdraw.domain.usecase

import com.billiardsdraw.billiardsdraw.data.repository.UserRepository
import javax.inject.Inject

/***
 * USERS API
 */

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository){
    suspend operator fun invoke() = userRepository.getUsers()
}

class GetUsersFromDBUseCase @Inject constructor(private val userRepository: UserRepository){
    suspend operator fun invoke() = userRepository.getUsersFromDB()
}