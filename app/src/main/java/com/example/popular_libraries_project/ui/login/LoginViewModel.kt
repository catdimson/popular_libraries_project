package com.example.popular_libraries_project.ui.login

import com.example.popular_libraries_project.domain.api.login.ForgotPasswordUsecase
import com.example.popular_libraries_project.domain.api.login.LoginUsecase
import com.example.popular_libraries_project.domain.api.login.LogoutUsecase
import com.example.popular_libraries_project.domain.api.login.RegisterUsecase
import com.example.popular_libraries_project.utils.Publisher
import com.example.popular_libraries_project.validations.ResponseStatus

class LoginViewModel(
    private val loginUsecase: LoginUsecase,
    private val logoutUsecase: LogoutUsecase,
    private val registerUsecase: RegisterUsecase,
    private val forgotPasswordUsecase: ForgotPasswordUsecase
): LoginContract.ViewModel {
    override val shouldShowProgress: Publisher<Boolean> = Publisher()
    override val isSuccess: Publisher<Boolean> = Publisher()
    override val errorText: Publisher<String?> = Publisher(true)
    override val isLogout: Publisher<Boolean> = Publisher()
    override val showForgotPassword: Publisher<Boolean> = Publisher()

    override fun onLogin(login: String, password: String) {
        shouldShowProgress.post(true)

        loginUsecase.login(login, password) { code ->
            val status = ResponseStatus.getStatusByCode(code)
            shouldShowProgress.post(false)
            if (status.isSuccess()) {
                isSuccess.post(true)
                errorText.post("")
            } else {
                isSuccess.post(false)
                errorText.post(status.getText())
            }
        }
    }

    override fun onRegistration(login: String, password: String) {
        shouldShowProgress.post(true)

        registerUsecase.register(login, password) { code ->
            shouldShowProgress.post(false)
            val status = ResponseStatus.getStatusByCode(code)

            if (status.isSuccess()) {
                view?.setSuccessRegistration(status.getText())
                isSuccess.post(true)
                errorText = ""
            } else {
                errorText.post(status.getText())
                isSuccess.post(false)
            }
        }
    }

    override fun onForgotPassword() {
        shouldShowProgress.post(true)
        showForgotPassword.post(true)
    }

    override fun onLogout() {
        shouldShowProgress.post(true)

        logoutUsecase.logout {
            isLogout.post(true)
            shouldShowProgress.post(false)
            isSuccess.post(false)
            errorText.post(ResponseStatus.getStatusByCode(it).getText())
        }
    }

    override fun onSendForgotPassword(email: String) {

    }
}