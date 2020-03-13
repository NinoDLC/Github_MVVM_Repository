package fr.delcey.github_mvvm_repository.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import fr.delcey.github_mvvm_repository.LoginApi

object ViewModelFactory : ViewModelProvider.NewInstanceFactory() {
    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when (modelClass) {
            MainViewModel::class.java -> MainViewModel(LoginApi.instance)
            else -> throw IllegalArgumentException("Unknown ViewModel class $modelClass")
        } as T
    }
}