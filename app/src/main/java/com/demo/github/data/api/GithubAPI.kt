package com.demo.github.data.api

import com.demo.github.data.model.PullRequestModel
import com.demo.github.data.model.UserModel
import com.demo.github.utils.PullRequestState
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GithubAPI {

    @GET("{full_url}")
    suspend fun getPullRequests(
        @Path(value="full_url", encoded = true)
        fullUrl : String,

        @Query("state")
        state : String = PullRequestState.state["closed"]?.get("state") as String,

        @Query("page")
        page : Int
    ) : Response<List<PullRequestModel>>

    @GET("{full_url}")
    suspend fun getUser(
        @Path(value="full_url", encoded = true)
        fullUrl : String,
    ) : Response<UserModel>

}