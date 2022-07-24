package com.demo.github.data.repository.user

import com.demo.github.data.api.Resource
import com.demo.github.data.model.UserModel

class UserDataSource(
    private val repository: UserRepository
) {
    
    suspend fun getUser() : Resource<UserModel> = repository.getUser()
    
}