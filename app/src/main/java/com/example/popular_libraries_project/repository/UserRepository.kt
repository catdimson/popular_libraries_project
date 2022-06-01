package com.example.popular_libraries_project.repository

import com.example.popular_libraries_project.model.user.User

interface UserRepository {

    fun findByLogin(login: String): User?

    fun findAll(): MutableList<User>

    fun create(user: User)

    fun update(user: User): User?

}