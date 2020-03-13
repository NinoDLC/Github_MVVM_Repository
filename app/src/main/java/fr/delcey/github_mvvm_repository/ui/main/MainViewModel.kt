package fr.delcey.github_mvvm_repository.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.delcey.github_mvvm_repository.LoginApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainViewModel(private val loginApi: LoginApi) : ViewModel() {

    private val uiModelLiveData = MutableLiveData<UiModel>()

    private var username : String? = null

    fun usernameChanged(username: String?) {
        this.username = username

        uiModelLiveData.value = UiModel(
            isLoading = true,
            listItems = null
        )

        viewModelScope.launch(Dispatchers.IO) {
            try {

                val githubRepos = loginApi.getRepos(username)

                withContext(Dispatchers.Main) {
                    uiModelLiveData.value = UiModel(
                        isLoading = false,
                        listItems = githubRepos.map {
                            ListItem(it.name, it.owner?.login, it.htmlUrl)
                        }
                    )
                }
            } catch (throwable : Throwable) {
                throwable.printStackTrace()
                withContext(Dispatchers.Main) {
                    uiModelLiveData.value = UiModel(
                        isLoading = false,
                        listItems = null
                    )
                }
            }
        }
    }

    fun getUiModelLiveData(): LiveData<UiModel> = uiModelLiveData

    fun refresh() {
        usernameChanged(username)
    }

    data class UiModel(
        val isLoading: Boolean,
        val listItems: List<ListItem>?
    )

    data class ListItem(
        val repositoryName: String?,
        val author: String?,
        val repositoryUrl: String?
    )
}
