package com.demo.github.data.repository.pullrequest

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.demo.github.data.api.GithubAPI
import com.demo.github.data.paging.PullRequestsPagingSource
import com.demo.github.utils.UrlConstants.REPO_URL


class PullRequestRepositoryImpl(
    private val api : GithubAPI
) : PullRequestRepository{

    override fun getPullRequests(state : String) =
        Pager(
            config = PagingConfig(
                pageSize = 20,
                maxSize = 100,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PullRequestsPagingSource(
                    state,
                    REPO_URL,
                    api
                )
            },
            initialKey = 1
        ).liveData

}