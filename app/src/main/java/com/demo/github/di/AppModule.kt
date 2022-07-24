package com.demo.github.di

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.demo.github.data.api.GithubAPI
import com.demo.github.data.repository.pullrequest.PullRequestDataSource
import com.demo.github.data.repository.pullrequest.PullRequestRepository
import com.demo.github.data.repository.pullrequest.PullRequestRepositoryImpl
import com.demo.github.data.repository.user.UserDataSource
import com.demo.github.data.repository.user.UserRepository
import com.demo.github.data.repository.user.UserRepositoryImpl
import com.demo.github.utils.UrlConstants
import com.demo.github.view.home.PullRequestAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit() : Retrofit =
        Retrofit.Builder()
            .baseUrl(UrlConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    

    @Provides
    @Singleton
    fun provideApi(retrofit: Retrofit) : GithubAPI =
        retrofit.create(GithubAPI::class.java)
    
    
    @Provides
    @Singleton
    fun providePullRequestRepositoryImpl(api : GithubAPI) : PullRequestRepositoryImpl =
        PullRequestRepositoryImpl(api)

    
    @Provides
    @Singleton
    fun providePullRequestDataSource(repo : PullRequestRepositoryImpl) =
        PullRequestDataSource(repo) as PullRequestRepository
    
    
    @Provides
    @Singleton
    fun provideUserRepositoryImpl(api : GithubAPI) : UserRepositoryImpl =
        UserRepositoryImpl(api)
    
    
    @Provides
    @Singleton
    fun provideUserDataSource(repository : UserRepositoryImpl) =
        UserDataSource(repository) as UserRepository
    
    
    @Provides
    @Singleton
    fun provideGlide(@ApplicationContext context : Context) : RequestManager =
        Glide.with(context)

    
    @Provides
    @Singleton
    fun provideAdapter(@ApplicationContext context: Context, glide: RequestManager) : PullRequestAdapter =
        PullRequestAdapter(context, glide)

}