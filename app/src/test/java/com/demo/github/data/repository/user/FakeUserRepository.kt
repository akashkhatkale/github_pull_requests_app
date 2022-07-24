package com.demo.github.data.repository.user

import com.demo.github.data.api.Resource
import com.demo.github.data.model.UserModel
import com.demo.github.exceptions.HttpException
import com.demo.github.exceptions.NoInternetConnectionException
import com.demo.github.exceptions.UnknownException
import com.demo.github.utils.ErrorConstants.HTTP_ERROR
import com.demo.github.utils.ErrorConstants.INTERNET_CONNECTION_ERROR

class FakeUserRepository : UserRepository {
    
    private var networkError : String? = null
    
    fun setNetworkError(error : String){
        networkError = error
    }
    
    override suspend fun getUser(): Resource<UserModel> {
        return if(networkError != null){
            when (networkError) {
                INTERNET_CONNECTION_ERROR -> {
                    Resource.Error(NoInternetConnectionException())
                }
                HTTP_ERROR -> {
                    Resource.Error(HttpException())
                }
                else -> {
                    Resource.Error(UnknownException())
                }
            }
        }else{
            Resource.Success(UserModel(
                login = "akashkhatkale",
                name = "Akash Khatkale",
                avatar_url = "avatar_url"
            ))
        }
    }
    
}