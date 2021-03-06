package com.example.popular_libraries_project.data.api.login

import com.example.popular_libraries_project.domain.api.login.LoginApi
import com.example.popular_libraries_project.domain.api.login.LogoutUsecase

class LogoutUsecaseImpl (
    private val api: LoginApi
) : LogoutUsecase {

    override fun logout(callback: (Int) -> Unit) {
        Thread {
            val result = api.logout()
            callback(result)
        }.start()
    }
}