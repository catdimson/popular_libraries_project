package com.example.popular_libraries_project.domain.api.login

import androidx.annotation.MainThread

interface RegisterUsecase {

    fun register(
        login: String,
        password: String,
        @MainThread callback: (Int) -> Unit
    )

}