package com.example.popular_libraries_project.presenter

import android.os.Handler
import android.os.Looper
import androidx.core.text.isDigitsOnly
import com.example.popular_libraries_project.model.user.User
import com.example.popular_libraries_project.repository.impl.InMemoryUserRepositoryImpl
import com.example.popular_libraries_project.validations.Validation
import com.example.popular_libraries_project.validations.ValidationResult
import com.example.popular_libraries_project.view.LoginContract
import java.lang.Thread.sleep
import java.util.*

class LoginPresenter: LoginContract.Presenter {
    private var repository: InMemoryUserRepositoryImpl = InMemoryUserRepositoryImpl.instance
    private var view: LoginContract.View? = null
    private val uiHandler = Handler(Looper.getMainLooper())
    private var isSuccess: Boolean = false
    private var errorText: String = ""

    override fun onAttach(view: LoginContract.View) {
        this.view = view
        if (isSuccess) {
            view.setSuccess()
        } else {
            view.setError(errorText)
        }
    }

    override fun onLogin(login: String, password: String) {
        view?.showProgress()
        Thread {
            sleep(2_000)
            uiHandler.post { // работа с вьюшками должна осуществляться из главного потока
                view?.hideProgress()
                if (checkCredentials(login, password)) {
                    view?.setSuccess()
                    isSuccess = true
                    errorText = ""
                } else {
                    view?.setError("Логин или пароль неверен")
                    isSuccess = false
                    errorText = "Логин или пароль неверен"
                }
            }
        }.start()
    }

    override fun onRegistration(login: String, password: String) {
        view?.showProgress()
        Thread {
            sleep(2_000)
            uiHandler.post { // работа с вьюшками должна осуществляться из главного потока
                view?.hideProgress()
                var validationResult = checkRegistration(login, password)
                if (validationResult.isValid()) {
                    createUser(login, password)
                    view?.setError("Успешно зарегистрированы")
                    isSuccess = true
                    errorText = ""
                } else {
                    view?.setError(validationResult.getTextError())
                    isSuccess = false
                    errorText = validationResult.getTextError()
                }
            }
        }.start()
    }

    override fun onForgotPassword() {
        TODO("Not yet implemented")
    }

    override fun onCredentialsChanged() {
        TODO("Not yet implemented")
    }

    override fun onLogout() {
        TODO("Not yet implemented")
    }

    private fun checkCredentials(login: String, password: String): Boolean {
        val user = repository.findByLogin(login)
        return user?.password == password && user.isActive
    }

    private fun createUser(login: String, password: String) {
        val user = User(null, login, password, Calendar.getInstance(), true)
        repository.create(user)
    }

    private fun checkRegistration(login: String, password: String): ValidationResult {
        val valResult = ValidationResult()
        if (login.isBlank() || login.isEmpty()) {
            val v = Validation(textError = "Введите логин")
            valResult.add(v)
        }
        if (login.length < 4) {
            val v = Validation(textError = "Логин должен содержать больше 3 символов")
            valResult.add(v)
        }
        if (login.isDigitsOnly()) {
            val v = Validation(textError = "Логин не должен состоять из одних чисел")
            valResult.add(v)
        }
        if (password.isDigitsOnly()) {
            val v = Validation(textError = "Пароль не должен состоять из одних чисел")
            valResult.add(v)
        }
        if (login.length < 6) {
            val v = Validation(textError = "Пароль должен содержать не меньше 6 символов")
            valResult.add(v)
        }
        return valResult
    }
}