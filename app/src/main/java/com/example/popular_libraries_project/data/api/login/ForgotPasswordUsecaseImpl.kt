package com.example.popular_libraries_project.data.api.login

import com.example.popular_libraries_project.domain.api.login.ForgotPasswordUsecase
import com.example.popular_libraries_project.domain.api.login.LoginApi

class ForgotPasswordUsecaseImpl(
    private val api: LoginApi
) : ForgotPasswordUsecase {

    override fun forgotPassword(email: String, callback: (Int) -> Unit) {
        Thread {
            val result = api.forgotPassword(email)
            callback(result)
        }.start()
    }

}