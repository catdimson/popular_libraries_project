package com.example.popular_libraries_project.domain.api.login

import androidx.annotation.MainThread

interface ForgotPasswordUsecase {

    fun forgotPassword(email: String, @MainThread callback: (Int) -> Unit)

}