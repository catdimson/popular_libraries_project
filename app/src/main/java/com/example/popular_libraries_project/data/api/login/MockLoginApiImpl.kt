package com.example.popular_libraries_project.data.api.login

import androidx.core.text.isDigitsOnly
import com.example.popular_libraries_project.domain.api.login.LoginApi
import com.example.popular_libraries_project.domain.entities.User
import com.example.popular_libraries_project.validations.Validation
import com.example.popular_libraries_project.validations.ValidationResult
import java.util.*

class MockLoginApiImpl: LoginApi {

    private var serverUserRepository: InMemoryServerUserRepositoryImpl = InMemoryServerUserRepositoryImpl()

    override fun login(login: String, password: String): Int {
        val user = serverUserRepository.findByLogin(login)
        if (user?.isActive == false) {
            return 3
        }
        return if (user?.password == password) {
            1
        } else {
            2
        }
    }

    override fun register(login: String, password: String): Int {
        var validationResult = checkRegistration(login, password)
        return if (validationResult == 21) {
            createUser(login, password)
            21
        } else {
            validationResult
        }
    }

    override fun logout(): Int {
        return 11
    }

    override fun forgotPassword(): Int {
        TODO("Not yet implemented")
    }

    // Интерфейс, имитирующий работу с юзером на удаленном сервере
    interface InMemoryServerUserRepository {

        fun findByLogin(login: String): User?

        fun findAll(): MutableList<User>

        fun create(user: User)

        fun update(user: User): User?

    }

    // Имплементация, имитирующая работу с юзером на удаленном сервере
    inner class InMemoryServerUserRepositoryImpl: InMemoryServerUserRepository {

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
    }

    private fun checkRegistration(login: String, password: String): Int {
        if (login.isBlank() || login.isEmpty()) {
            return 22
        }
        if (login.length < 4) {
            return 23
        }
        if (login.isDigitsOnly()) {
            return 24
        }
        if (!(password.isBlank() || password.isEmpty()) && password.isDigitsOnly()) {
            return 25
        }
        if (password.length < 6) {
            return 26
        }
        if (serverUserRepository.findByLogin(login) != null) {
            return 27
        }
        return 21
    }

    private fun createUser(login: String, password: String) {
        val user = User(null, login, password, Calendar.getInstance(), true)
        serverUserRepository.create(user)
    }
}