package com.billiardsdraw.billiardsdraw.domain.usecase

import com.billiardsdraw.billiardsdraw.data.repository.BilliardsDrawRepository
import javax.inject.Inject

class BilliardsDrawUseCase @Inject constructor(private val repository: BilliardsDrawRepository) {
    suspend operator fun invoke() = repository.get()
}