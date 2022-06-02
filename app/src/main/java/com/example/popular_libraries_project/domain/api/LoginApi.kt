package com.example.popular_libraries_project.domain.api

import androidx.annotation.WorkerThread

interface LoginApi {

    @WorkerThread
    fun login(login: String, password: String): Boolean

    @WorkerThread
    fun register(login: String, password: String): Boolean

    @WorkerThread
    fun logout(): Boolean

    @WorkerThread
    fun forgotPassword(): Boolean

}