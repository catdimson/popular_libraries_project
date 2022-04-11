package com.example.popular_libraries_project.view

import androidx.annotation.MainThread

class LoginContract {

    interface View {
        @MainThread
        fun setSuccess()
        @MainThread
        fun setError(error: String)
        @MainThread
        fun showProgress()
        @MainThread
        fun hideProgress()
    }

    interface Presenter {
        // почти все методы начинаются на on...
        fun onAttach(view: View)
        fun onLogin(login: String, password: String)
        fun onRegistration(login: String, password: String)
        fun onForgotPassword()
        fun onCredentialsChanged()
        fun onLogout()
    }
}