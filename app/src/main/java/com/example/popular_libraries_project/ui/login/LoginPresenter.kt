package com.example.popular_libraries_project.ui.login

import com.example.popular_libraries_project.domain.api.LoginUsecase
import com.example.popular_libraries_project.validations.ResponseStatus

class LoginPresenter(
    private val loginUsecase: LoginUsecase
): LoginContract.Presenter {
    private var view: LoginContract.View? = null
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

        loginUsecase.login(login, password) { code ->
            view?.hideProgress()
            val status = ResponseStatus.getStatusByCode(code)
            if (status.isSuccess()) {
                view?.setSuccess()
                isSuccess = status.isSuccess()
                errorText = status.getText()
            } else {
                view?.setError(status.getText())
                isSuccess = status.isSuccess()
                errorText = status.getText()
            }
        }
    }

    override fun onRegistration(login: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun onForgotPassword() {
        TODO("Not yet implemented")
    }

    override fun onLogout() {
        TODO("Not yet implemented")
    }

    override fun onSendForgotPassword(email: String) {
        TODO("Not yet implemented")
    }


}