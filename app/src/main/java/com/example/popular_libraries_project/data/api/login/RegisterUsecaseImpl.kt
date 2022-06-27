package com.example.popular_libraries_project.data.api.login

import android.os.Handler
import com.example.popular_libraries_project.domain.api.login.LoginApi
import com.example.popular_libraries_project.domain.api.login.RegisterUsecase

class RegisterUsecaseImpl(
    private val api: LoginApi,
    private val uiHandler: Handler
) : RegisterUsecase {

    override fun register(login: String, password: String, callback: (Int) -> Unit) {
        Thread {
            uiHandler.post {
                val result = api.register(login, password)
                callback(result)
            }
        }.start()
    }

}