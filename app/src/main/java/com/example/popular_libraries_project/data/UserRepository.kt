package com.example.popular_libraries_project.data

import com.example.popular_libraries_project.domain.entity.User

interface UserRepository {

    fun findByLogin(login: String): User?

    fun findAll(): MutableList<User>

    fun create(user: User)

    fun update(user: User): User?

}