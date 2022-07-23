package com.demo.github.data.api

import androidx.paging.PagingSource
import com.demo.github.data.model.PullRequestModel
import com.demo.github.exceptions.HttpException
import com.demo.github.exceptions.NoInternetConnectionException
import java.io.IOException


class PullRequestsPagingSource(
    private val state : String,
    private val fullUrl : String,
    private val api : GithubAPI
) : PagingSource<Int, PullRequestModel>(){

    companion object {
        const val STARTING_PAGE = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, PullRequestModel> {
        val position = params.key ?: STARTING_PAGE

        return try{
            val response = api.getPullRequests(fullUrl, state, position)
            val result = response.body() ?: listOf()

            LoadResult.Page(
                data = result,
                prevKey = if(position == STARTING_PAGE) null else position - 1,
                nextKey = if(result.isEmpty()) null else position + 1
            )
        }catch (exception: IOException){
            LoadResult.Error(NoInternetConnectionException())
        }catch (exception: HttpException){
            LoadResult.Error(HttpException())
        }
    }
}