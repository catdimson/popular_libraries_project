package com.example.popular_libraries_project.domain.entities

import java.util.*

data class User (
    var id: Int?,
    var login: String,
    var password: String,
    var createDate: Calendar,
    var isActive: Boolean
)
