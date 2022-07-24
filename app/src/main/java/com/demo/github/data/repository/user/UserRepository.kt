package com.demo.github.data.repository.user

import com.demo.github.data.api.Resource
import com.demo.github.data.model.UserModel

interface UserRepository {
    
    suspend fun getUser() : Resource<UserModel>
    
}