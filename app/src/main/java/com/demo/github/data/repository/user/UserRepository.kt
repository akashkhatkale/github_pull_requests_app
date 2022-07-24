package com.demo.github.data.repository.user

import com.demo.github.data.api.GithubAPI
import com.demo.github.data.api.Resource
import com.demo.github.data.model.UserModel
import com.demo.github.exceptions.HttpException
import com.demo.github.exceptions.NoInternetConnectionException
import com.demo.github.exceptions.UnknownException
import com.demo.github.utils.UrlConstants
import java.io.IOException

class UserRepository(
    private val api : GithubAPI
) {
    
    suspend fun getUser() : Resource<UserModel> {
        return try {
            val response = api.getUser(UrlConstants.USER_URL)
            if(response.isSuccessful){
                response.body()?.let{res->
                    return Resource.Success(data = res)
                }
            }
            Resource.Error(UnknownException())
        }catch (exception : IOException){
            Resource.Error(message = NoInternetConnectionException())
        }catch (exception : retrofit2.HttpException){
            Resource.Error(message = HttpException())
        }
        
    }
}