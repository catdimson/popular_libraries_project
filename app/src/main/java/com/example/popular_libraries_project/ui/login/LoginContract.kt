package com.example.popular_libraries_project.ui.login

import androidx.annotation.MainThread

class LoginContract {

    interface View {
        @MainThread
        fun setSuccess()

        @MainThread
        fun setError(error: String)

        @MainThread
        fun setSuccessRegistration(text: String)

        @MainThread
        fun setSuccessForgot(email: String)

        @MainThread
        fun showProgress()

        @MainThread
        fun hideProgress()

        @MainThread
        fun setLogout()

        @MainThread
        fun setForgotPassword()

        @MainThread
        fun setErrorForgotPassword(error: String)
    }

    interface Presenter {
        // почти все методы начинаются на on...
        @MainThread
        fun onAttach(view: View)

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