package com.demo.github.data.repository


class RemoteDataSource(
    private val repository: RemoteRepository
){

    fun getPullRequests(state : String) = repository.getPullRequests(state)

    suspend fun getUser() = repository.getUser()

}