package com.demo.github.viewmodel

import com.demo.github.data.repository.pullrequest.FakePullRequestRepository
import com.demo.github.data.repository.user.FakeUserRepository
import org.junit.Assert.*
import org.junit.Before

class MainViewModelTest {

    private lateinit var viewModel: MainViewModel
    
    @Before
    fun setup(){
        viewModel = MainViewModel(FakePullRequestRepository(), FakeUserRepository())
    }
    
    

}