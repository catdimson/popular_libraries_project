package com.example.popular_libraries_project.data.api.login

import com.example.popular_libraries_project.domain.api.login.LoginApi

class WebLoginApiImpl: LoginApi {
    override fun login(login: String, password: String): Int {
        TODO("Not yet implemented")
    }

    override fun register(login: String, password: String): Int {
        TODO("Not yet implemented")
    }

    override fun logout(): Int {
        TODO("Not yet implemented")
    }

    override fun forgotPassword(email: String): Int {
        TODO("Not yet implemented")
    }
}