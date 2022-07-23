package com.demo.github.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.demo.github.data.api.Resource
import com.demo.github.data.model.PullRequestModel
import com.demo.github.data.model.UserModel
import com.demo.github.data.repository.RemoteDataSource
import com.demo.github.exceptions.NoInternetConnectionException
import com.demo.github.utils.Constants
import com.demo.github.utils.Constants.DEFAULT_STATE
import com.demo.github.utils.PullRequestState
import com.demo.github.utils.handleResponseResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private var remote : RemoteDataSource
) : ViewModel() {

    // private variables
    private lateinit var _pullRequests : LiveData<PagingData<PullRequestModel>>
    private var _state = MutableLiveData(PullRequestState.state["closed"]?.get("state") as String)
    private var _user = MutableLiveData<Resource<UserModel>>()

    // public variables
    val pullRequestsValue : LiveData<PagingData<PullRequestModel>>
    get() = _pullRequests
    val stateValue : LiveData<String>
    get() = _state
    val userValue : LiveData<Resource<UserModel>>
    get() = _user


    init {
        loadPullRequestsFeed(_state.value ?: DEFAULT_STATE)
        loadUser()
    }

    private fun loadUser() = viewModelScope.launch {
        _user.postValue(Resource.Loading())
        try{
            val response = remote.getUser()
            val responseResult = handleResponseResult(response)
            _user.postValue(responseResult)
        }catch (exception: IOException){
            _user.postValue(Resource.Error(NoInternetConnectionException()))
        }catch (exception: HttpException){
            _user.postValue(Resource.Error(com.demo.github.exceptions.HttpException()))
        }
    }

    private fun loadPullRequestsFeed(state : String) = viewModelScope.launch {
        val response = remote.getPullRequests(state).cachedIn(viewModelScope)
        _pullRequests = response
    }

}