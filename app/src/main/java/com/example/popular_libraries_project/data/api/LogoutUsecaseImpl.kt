package com.example.popular_libraries_project.data.api

import android.os.Handler
import com.example.popular_libraries_project.domain.api.LoginApi
import com.example.popular_libraries_project.domain.api.LoginUsecase
import com.example.popular_libraries_project.domain.api.LogoutUsecase

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