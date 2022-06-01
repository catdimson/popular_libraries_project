package com.example.popular_libraries_project.view

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
        fun onAttach(view: View)
        fun onLogin(login: String, password: String)
        fun onRegistration(login: String, password: String)
        fun onForgotPassword()
        fun onLogout()
        fun onSendForgotPassword(email: String)
    }
}