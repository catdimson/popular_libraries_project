package com.example.popular_libraries_project.domain.api

import androidx.annotation.MainThread

interface LoginUsecase {

    fun login(
        login: String,
        password: String,
        @MainThread callback: (Boolean) -> Unit
    )

}