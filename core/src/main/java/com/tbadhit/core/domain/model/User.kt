package com.tbadhit.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class User(
    val id: Int? = 0,

    val username: String? = null,

    val company: String? = null,

    val publicRepos: Int? = null,

    val followers: Int? = null,

    val avatarUrl: String? = null,

    val following: Int? = null,

    val name: String? = null,

    val location: String? = null,

    var isFavorite: Boolean?
) : Parcelable
