package com.example.popular_libraries_project.domain.api

import androidx.annotation.WorkerThread

interface LoginApi {

    @WorkerThread
    fun login(login: String, password: String): Int

    @WorkerThread
    fun register(login: String, password: String): Int

    @WorkerThread
    fun logout(): Int

    @WorkerThread
    fun forgotPassword(): Int

}