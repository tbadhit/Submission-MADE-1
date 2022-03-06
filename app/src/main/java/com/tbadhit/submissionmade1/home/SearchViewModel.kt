package com.tbadhit.submissionmade1.home

import androidx.lifecycle.*
import com.tbadhit.core.data.Resource
import com.tbadhit.core.domain.model.User
import com.tbadhit.core.domain.usecase.UserUseCase

class SearchViewModel(userUseCase: UserUseCase) : ViewModel() {

    private var username: MutableLiveData<String> = MutableLiveData()

    fun setSearch(query: String) {
        if (username.value == query) {
            return
        }
        username.value = query
    }

    val users: LiveData<Resource<List<User>>> = Transformations
        .switchMap(username) {
            userUseCase.getSearchUsers(it).asLiveData()
        }
}