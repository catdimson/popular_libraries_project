package com.example.popular_libraries_project.ui.login

import android.os.Handler
import android.os.Looper
import androidx.core.text.isDigitsOnly
import com.example.popular_libraries_project.domain.entities.User
import com.example.popular_libraries_project.data.repository.InMemoryUserRepositoryImpl
import com.example.popular_libraries_project.validations.Validation
import com.example.popular_libraries_project.validations.ValidationResult
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
                    view?.setSuccessRegistration("Регистрация пользователя прошла успешно")
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
        view?.showProgress()
        Thread {
            sleep(500)
            uiHandler.post {
                view?.setForgotPassword()
            }
        }.start()
    }

    override fun onLogout() {
        view?.showProgress()
        Thread {
            sleep(2_000)
            uiHandler.post { // работа с вьюшками должна осуществляться из главного потока
                view?.setLogout()
                view?.hideProgress()
                isSuccess = false
                errorText = ""
            }
        }.start()
    }

    override fun onSendForgotPassword(email: String) {
        view?.showProgress()
        Thread {
            sleep(1_500)
            uiHandler.post { // работа с вьюшками должна осуществляться из главного потока
                var validationResult = checkEmail(email)
                if (validationResult.isValid()) {
                    view?.setSuccessForgot("Пароль успешно отправлен на почту: $email")
                    isSuccess = true
                    errorText = ""
                } else {
                    view?.setErrorForgotPassword(validationResult.getTextError())
                    isSuccess = false
                    errorText = validationResult.getTextError()
                }
            }
        }.start()
    }

    private fun checkCredentials(login: String, password: String): Boolean {
        val user = repository.findByLogin(login)
        return user?.password == password && user.isActive
    }

    private fun createUser(login: String, password: String) {
        val user = User(null, login, password, Calendar.getInstance(), true)
        repository.create(user)
    }

    private fun checkEmail(email: String): ValidationResult {
        val valResult = ValidationResult()
        if (Regex("""\w{6,}@(yandex.ru|gmail.com|mail.ru)""").find(email) == null) {
            val v = Validation(textError = "Некорректный адрес электронной почты")
            valResult.add(v)
        }
        return valResult
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
        if (repository.findByLogin(login) != null) {
            val v = Validation(textError = "Пользователь с таким именем уже зарегистрирован в системе")
            valResult.add(v)
        }
        return valResult
    }
}