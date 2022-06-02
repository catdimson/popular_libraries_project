package com.example.popular_libraries_project.data.api.login

import android.os.Handler
import androidx.annotation.MainThread
import com.example.popular_libraries_project.domain.api.login.LoginApi
import com.example.popular_libraries_project.domain.api.login.LoginUsecase

class LoginUsecaseImpl(
    private val api: LoginApi,
    private val uiHandler: Handler
) : LoginUsecase {

    override fun login(login: String, password: String, @MainThread callback: (Int) -> Unit) {
        Thread {
            val result = api.login(login, password)
            uiHandler.post {
                callback(result)
            }
        }.start()
    }

}