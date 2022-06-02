package com.example.popular_libraries_project

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.popular_libraries_project.data.api.LoginUsecaseImpl
import com.example.popular_libraries_project.data.api.MockLoginApiImpl
import com.example.popular_libraries_project.domain.api.LoginApi
import com.example.popular_libraries_project.domain.api.LoginUsecase

class App : Application() {
    private val loginApi: LoginApi by lazy {
        MockLoginApiImpl()
    }

    val loginUsecase: LoginUsecase by lazy {
        LoginUsecaseImpl(app.loginApi, Handler(Looper.getMainLooper()))
    }

}

val Context.app: App
    get() {
        return applicationContext as App
    }
