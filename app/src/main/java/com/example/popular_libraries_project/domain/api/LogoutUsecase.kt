package com.example.popular_libraries_project.domain.api

import androidx.annotation.MainThread

interface LogoutUsecase {

    fun logout(@MainThread callback: (Int) -> Unit)

}