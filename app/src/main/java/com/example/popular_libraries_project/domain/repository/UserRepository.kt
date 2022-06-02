package com.example.popular_libraries_project.domain.repository

import com.example.popular_libraries_project.domain.entities.User

interface UserRepository {

    fun findByLogin(login: String): User?

    fun findAll(): MutableList<User>

    fun create(user: User)

    fun update(user: User): User?

}