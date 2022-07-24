package com.demo.github.data.repository.pullrequest

import androidx.lifecycle.LiveData
import androidx.paging.PagingData
import com.demo.github.data.model.PullRequestModel

interface PullRequestRepository {
    
    fun getPullRequests(state : String) : LiveData<PagingData<PullRequestModel>>

}