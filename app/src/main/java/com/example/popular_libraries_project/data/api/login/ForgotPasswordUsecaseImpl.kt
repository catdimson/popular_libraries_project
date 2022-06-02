package com.example.popular_libraries_project.data.api.login

import android.os.Handler
import com.example.popular_libraries_project.domain.api.login.ForgotPasswordUsecase
import com.example.popular_libraries_project.domain.api.login.LoginApi

class ForgotPasswordUsecaseImpl(
    private val api: LoginApi,
    private val uiHandler: Handler
) : ForgotPasswordUsecase {

    override fun forgotPassword(email: String, callback: (Int) -> Unit) {
        Thread {
            uiHandler.post {
                val result = api.forgotPassword(email)
                callback(result)
            }
        }.start()
    }

}