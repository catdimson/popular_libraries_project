package com.example.popular_libraries_project

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.popular_libraries_project.data.api.login.LoginUsecaseImpl
import com.example.popular_libraries_project.data.api.login.LogoutUsecaseImpl
import com.example.popular_libraries_project.data.api.login.MockLoginApiImpl
import com.example.popular_libraries_project.data.api.login.RegisterUsecaseImpl
import com.example.popular_libraries_project.domain.api.login.LoginApi
import com.example.popular_libraries_project.domain.api.login.LoginUsecase
import com.example.popular_libraries_project.domain.api.login.LogoutUsecase
import com.example.popular_libraries_project.domain.api.login.RegisterUsecase

class App : Application() {
    private val loginApi: LoginApi by lazy {
        MockLoginApiImpl()
    }

    val loginUsecase: LoginUsecase by lazy {
        LoginUsecaseImpl(app.loginApi, Handler(Looper.getMainLooper()))
    }

    val logoutUsecase: LogoutUsecase by lazy {
        LogoutUsecaseImpl(app.loginApi, Handler(Looper.getMainLooper()))
    }

    val registerUsecase: RegisterUsecase by lazy {
        RegisterUsecaseImpl(app.loginApi, Handler(Looper.getMainLooper()))
    }

}

val Context.app: App
    get() {
        return applicationContext as App
    }
