package com.example.popular_libraries_project.data.api.login

import androidx.annotation.MainThread
import com.example.popular_libraries_project.domain.api.login.LoginApi
import com.example.popular_libraries_project.domain.api.login.LoginUsecase

class LoginUsecaseImpl(
    private val api: LoginApi
) : LoginUsecase {

    override fun login(login: String, password: String, @MainThread callback: (Int) -> Unit) {
        Thread {
            val result = api.login(login, password)
            callback(result)
        }.start()
    }

}