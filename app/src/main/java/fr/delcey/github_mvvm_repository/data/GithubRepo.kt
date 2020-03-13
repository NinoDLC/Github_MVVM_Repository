package fr.delcey.github_mvvm_repository.data

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class GithubRepo(
    @SerializedName("html_url") val htmlUrl: String? = null,
    @SerializedName("name") val name: String? = null,
    @SerializedName("owner") val owner: Owner? = null
)