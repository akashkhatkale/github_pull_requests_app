package com.demo.github.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.demo.github.data.api.Resource
import com.demo.github.data.model.PullRequestModel
import com.demo.github.data.model.UserModel
import com.demo.github.data.repository.pullrequest.PullRequestDataSource
import com.demo.github.data.repository.pullrequest.PullRequestRepository
import com.demo.github.data.repository.user.UserDataSource
import com.demo.github.data.repository.user.UserRepository
import com.demo.github.utils.Constants.DEFAULT_STATE
import com.demo.github.utils.PullRequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pullRequestsDataSource : PullRequestRepository,
    private val userDataSource: UserRepository
) : ViewModel() {

    private lateinit var _pullRequests : LiveData<PagingData<PullRequestModel>>
    val pullRequestsValue : LiveData<PagingData<PullRequestModel>>
        get() = _pullRequests
    
    
    private var _state = MutableLiveData(PullRequestState.state["closed"]?.get("state") as String)
    val stateValue : LiveData<String>
        get() = _state
    
    
    private var _user = MutableLiveData<Resource<UserModel>>()
    val userValue : LiveData<Resource<UserModel>>
        get() = _user
    

    init {
        loadPullRequestsFeed(_state.value ?: DEFAULT_STATE)
        loadUser()
    }

    fun loadUser() = viewModelScope.launch {
        _user.postValue(Resource.Loading())
        val response = userDataSource.getUser()
        _user.postValue(response)
    }

    private fun loadPullRequestsFeed(state : String) = viewModelScope.launch {
        val response = pullRequestsDataSource.getPullRequests(state).cachedIn(viewModelScope)
        _pullRequests = response
    }

}