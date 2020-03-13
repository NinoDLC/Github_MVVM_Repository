package fr.delcey.github_mvvm_repository

import fr.delcey.github_mvvm_repository.data.GithubRepo
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface LoginApi {
    companion object {
        private const val BASE_URL = "https://api.github.com"

        val instance: LoginApi by lazy {
            Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(
                    OkHttpClient.Builder()
                        .addInterceptor(HttpLoggingInterceptor().apply {
                            level = HttpLoggingInterceptor.Level.BODY
                        })
                        .build()
                )
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LoginApi::class.java)
        }
    }

    @GET("users/{user}/repos")
    suspend fun getRepos(@Path("user") user: String?): List<GithubRepo>
}