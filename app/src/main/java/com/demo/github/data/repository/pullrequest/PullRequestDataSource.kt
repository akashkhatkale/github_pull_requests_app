package com.demo.github.data.repository.pullrequest

import com.demo.github.data.api.Resource
import com.demo.github.data.model.UserModel


class PullRequestDataSource(
    private val repository: PullRequestRepository
){

    fun getPullRequests(state : String) = repository.getPullRequests(state)
    
}