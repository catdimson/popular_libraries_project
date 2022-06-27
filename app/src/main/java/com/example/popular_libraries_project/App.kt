package com.example.popular_libraries_project

import android.app.Application
import android.content.Context
import android.os.Handler
import android.os.Looper
import com.example.popular_libraries_project.data.api.login.*
import com.example.popular_libraries_project.domain.api.login.*

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

    val forgotPasswordUsecase: ForgotPasswordUsecase by lazy {
        ForgotPasswordUsecaseImpl(app.loginApi, Handler(Looper.getMainLooper()))
    }

}

val Context.app: App
    get() {
        return applicationContext as App
    }
