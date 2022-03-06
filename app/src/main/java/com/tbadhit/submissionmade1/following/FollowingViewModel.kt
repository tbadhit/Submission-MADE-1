package com.tbadhit.submissionmade1.following

import androidx.lifecycle.*
import com.tbadhit.core.data.Resource
import com.tbadhit.core.domain.model.User
import com.tbadhit.core.domain.usecase.UserUseCase

class FollowingViewModel(userUseCase: UserUseCase) : ViewModel() {
    private var username: MutableLiveData<String> = MutableLiveData()

    fun setFollowing(user: String) {
        if (username.value == user) return

        username.value = user
    }

    val userFollowing: LiveData<Resource<List<User>>> = Transformations.switchMap(username) {
        userUseCase.getListFollowing(it).asLiveData()
    }
}