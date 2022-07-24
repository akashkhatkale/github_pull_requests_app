package com.demo.github.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.demo.github.data.model.UserModel
import com.demo.github.data.repository.pullrequest.FakePullRequestRepository
import com.demo.github.data.repository.user.FakeUserRepository
import com.demo.github.exceptions.HttpException
import com.demo.github.exceptions.NoInternetConnectionException
import com.demo.github.getOrAwaitValueTest
import com.demo.github.utils.ErrorConstants.HTTP_ERROR
import com.demo.github.utils.ErrorConstants.INTERNET_CONNECTION_ERROR
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestWatcher
import org.junit.runner.Description

class MainViewModelTest {
    
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()
    
    @OptIn(ExperimentalCoroutinesApi::class)
    @get:Rule
    val coroutineRule = MainCoroutineRule()

    private lateinit var viewModel: MainViewModel
    
    private var fakeUserRepository = FakeUserRepository()
    
    @Before
    fun setup(){
        viewModel = MainViewModel(FakePullRequestRepository(), fakeUserRepository)
    }
    
    @Test
    fun `load user without any error`(){
        viewModel.loadUser()
        
        val user = viewModel.userValue.getOrAwaitValueTest()
        
        assertEquals(user.data, UserModel(
            login = "akashkhatkale",
            name = "Akash Khatkale",
            avatar_url = "avatar_url"
        ))
        assertEquals(user.message, null)
    }
    
    @Test
    fun `load user with internet error`(){
        fakeUserRepository.setNetworkError(INTERNET_CONNECTION_ERROR)
        viewModel.loadUser()
        
        val user = viewModel.userValue.getOrAwaitValueTest()
        
        assertEquals(user.data, null)
        assertEquals(user.message?.message, NoInternetConnectionException().message)
    }
    
    @Test
    fun `load user with http error`(){
        fakeUserRepository.setNetworkError(HTTP_ERROR)
        viewModel.loadUser()
        
        val user = viewModel.userValue.getOrAwaitValueTest()
        
        assertEquals(user.data, null)
        assertEquals(user.message?.message, HttpException().message)
    }

}


@ExperimentalCoroutinesApi
class MainCoroutineRule(
    private val dispatcher: TestCoroutineDispatcher = TestCoroutineDispatcher()
) : TestWatcher(), TestCoroutineScope by TestCoroutineScope(dispatcher) {
    override fun starting(description: Description?) {
        super.starting(description)
        Dispatchers.setMain(dispatcher)
    }
    
    override fun finished(description: Description?) {
        super.finished(description)
        cleanupTestCoroutines()
        Dispatchers.resetMain()
    }
}