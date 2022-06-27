package com.example.popular_libraries_project.ui.login

import androidx.annotation.MainThread
import com.example.popular_libraries_project.utils.Publisher

class LoginContract {

    interface ViewModel {

        val shouldShowProgress: Publisher<Boolean>
        val isSuccess: Publisher<Boolean>
        val errorText: Publisher<String?>
        val isLogout: Publisher<Boolean>

        @MainThread
        fun onLogin(login: String, password: String)

        @MainThread
        fun onRegistration(login: String, password: String)

        @MainThread
        fun onForgotPassword()

        @MainThread
        fun onLogout()

        @MainThread
        fun onSendForgotPassword(email: String)
    }
}