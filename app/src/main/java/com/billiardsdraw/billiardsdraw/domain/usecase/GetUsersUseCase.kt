package com.billiardsdraw.billiardsdraw.domain.usecase

import com.billiardsdraw.billiardsdraw.data.repository.UserRepository
import javax.inject.Inject

class GetUsersUseCase @Inject constructor(private val userRepository: UserRepository){
    suspend operator fun invoke() = userRepository.getUsers()
    suspend fun getUsersFromDB() = userRepository.getUsersFromDB()
}