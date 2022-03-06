package com.tbadhit.submissionmade1.di

import com.tbadhit.core.domain.usecase.UserInteractor
import com.tbadhit.core.domain.usecase.UserUseCase
import com.tbadhit.submissionmade1.detail.DetailViewModel
import com.tbadhit.submissionmade1.follower.FollowerViewModel
import com.tbadhit.submissionmade1.following.FollowingViewModel
import com.tbadhit.submissionmade1.home.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val useCaseModule = module {
    factory<UserUseCase> { UserInteractor(get()) }
}

val viewModelModule = module {
    viewModel { SearchViewModel(get()) }
    viewModel { FollowerViewModel(get()) }
    viewModel { FollowingViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}