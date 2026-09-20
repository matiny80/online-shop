package ir.matinyakhshi.onlineshop.domain.repository

import ir.matinyakhshi.onlineshop.domain.model.User
import kotlin.Result

interface AuthRepository {

    suspend fun login(
        phoneNumber: String
    ): Result<User>

    suspend fun verifyCode(
        phoneNumber: String,
        code: String
    ): Result<User>

    suspend fun logout()

    suspend fun getCurrentUser(): User?
}