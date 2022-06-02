package com.example.popular_libraries_project.data.api.login

import android.os.Handler
import com.example.popular_libraries_project.domain.api.login.LoginApi
import com.example.popular_libraries_project.domain.api.login.LogoutUsecase

class LogoutUsecaseImpl (
    private val api: LoginApi,
    private val uiHandler: Handler
) : LogoutUsecase {

    override fun logout(callback: (Int) -> Unit) {
        Thread {
            uiHandler.post {
                val result = api.logout()
                callback(result)
            }
        }.start()
    }
}