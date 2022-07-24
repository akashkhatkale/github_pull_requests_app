package com.demo.github.data.repository.pullrequest


class PullRequestDataSource(
    private val repository: PullRequestRepositoryImpl
) : PullRequestRepository{

    override fun getPullRequests(state : String) = repository.getPullRequests(state)
    
}