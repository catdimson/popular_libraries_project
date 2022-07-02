package com.example.popular_libraries_project.ui.login

import androidx.annotation.MainThread
import com.example.popular_libraries_project.utils.Publisher

class LoginContract {

    interface ViewModel {

        val shouldShowProgress: Publisher<Boolean>
        val isSuccess: Publisher<Boolean>
        val errorText: Publisher<String?>
        val isLogout: Publisher<Boolean>
        val showForgotPassword: Publisher<Boolean>
        val registration: Publisher<Boolean>
        val sendForgotPassword: Publisher<Boolean>
        val successText: Publisher<String?>

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