package com.tbadhit.submissionmade1.follower

import androidx.lifecycle.*
import com.tbadhit.core.data.Resource
import com.tbadhit.core.domain.model.User
import com.tbadhit.core.domain.usecase.UserUseCase

class FollowerViewModel(userUseCase: UserUseCase) : ViewModel() {
    private var username: MutableLiveData<String> = MutableLiveData()

    fun setFollower(user: String) {
        if (username.value == user) return

        username.value = user
    }

    val userFollowers: LiveData<Resource<List<User>>> = Transformations.switchMap(username) {
        userUseCase.getListFollowers(it).asLiveData()
    }
}