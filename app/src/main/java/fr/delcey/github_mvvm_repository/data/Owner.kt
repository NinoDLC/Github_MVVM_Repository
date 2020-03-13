package fr.delcey.github_mvvm_repository.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Owner(
    @SerializedName("avatar_url") val avatarUrl: String? = null,
    @SerializedName("html_url") val htmlUrl: String? = null,
    @SerializedName("id") val id: Int? = null,
    @SerializedName("login") val login: String? = null,
    @SerializedName("repos_url") val reposUrl: String? = null,
    @SerializedName("url") val url: String? = null
)