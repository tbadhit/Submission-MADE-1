package com.tbadhit.submissionmade1.detail

import androidx.lifecycle.*
import com.tbadhit.core.domain.model.User
import com.tbadhit.core.domain.usecase.UserUseCase
import kotlinx.coroutines.launch

class DetailViewModel(private val userUseCase: UserUseCase) : ViewModel() {


    fun getDetailUser(username: String) = userUseCase.getDetailUser(username).asLiveData()

    fun checkUser(id: Int) = userUseCase.check(id)?.asLiveData()

    fun addToFavorite(username: String?, id: Int?, avatarUrl: String?, isFavorite: Boolean?) =
        viewModelScope.launch {
            userUseCase.insert(username, id, avatarUrl, isFavorite)
        }

    fun removeFromFavorite(user: User) = viewModelScope.launch {
        userUseCase.delete(user)
    }
}