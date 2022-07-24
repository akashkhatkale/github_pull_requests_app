package com.demo.github.data.repository.pullrequest

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.paging.PagingData
import com.demo.github.data.model.PullRequestModel

class FakePullRequestRepository : PullRequestRepository{
    
    override fun getPullRequests(state: String): LiveData<PagingData<PullRequestModel>> {
        return liveData {  }
    }

}
