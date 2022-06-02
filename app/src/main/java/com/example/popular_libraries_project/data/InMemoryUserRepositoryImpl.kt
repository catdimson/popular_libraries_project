package com.example.popular_libraries_project.data

import com.example.popular_libraries_project.domain.entities.User
import com.example.popular_libraries_project.domain.repository.UserRepository
import java.util.*

class InMemoryUserRepositoryImpl: UserRepository {

    private val users: MutableList<User> = mutableListOf(
        User(1, "user1", "password1", GregorianCalendar(2022, 1, 10), true),
        User(2, "user2", "password2", GregorianCalendar(2021, 2, 15), true),
        User(3, "user3", "password3", GregorianCalendar(2021, 6, 3), false),
        User(4, "user4", "password4", GregorianCalendar(2020, 11, 11), true),
        User(5, "user5", "password5", GregorianCalendar(2021, 9, 25), false),
    )

    override fun findByLogin(login: String): User? {
        return users.find { user -> user.login == login }
    }

    override fun findAll() = users

    override fun create(user: User) {
        val size = users.size
        if (size > 0) {
            val id = users[size - 1].id
            user.id = id
        }
        users.add(user)
    }

    override fun update(user: User): User? {
        val updatedUser = findByLogin(user.login)
        updatedUser?.login = user.login
        updatedUser?.password = user.password
        return updatedUser
    }

    companion object {
        val instance = InMemoryUserRepositoryImpl()
    }
}

