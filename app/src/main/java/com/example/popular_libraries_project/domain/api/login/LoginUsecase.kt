package com.example.popular_libraries_project.domain.api.login

import androidx.annotation.MainThread

interface LoginUsecase {

    fun login(
        login: String,
        password: String,
        @MainThread callback: (Int) -> Unit
    )

}