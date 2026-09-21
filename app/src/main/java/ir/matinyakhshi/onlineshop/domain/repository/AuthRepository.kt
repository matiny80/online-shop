package ir.matinyakhshi.onlineshop.domain.repository

import ir.matinyakhshi.onlineshop.core.util.Resource
import ir.matinyakhshi.onlineshop.domain.model.User

interface AuthRepository {

    suspend fun login(
        phoneNumber: String
    ): Resource<User>

    suspend fun verifyCode(
        phoneNumber: String,
        code: String
    ): Resource<User>

    suspend fun logout()

    suspend fun getCurrentUser(): User?
}