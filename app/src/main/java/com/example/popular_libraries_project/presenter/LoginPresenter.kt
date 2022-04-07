package com.example.popular_libraries_project.presenter

import android.os.Handler
import android.os.Looper
import com.example.popular_libraries_project.view.LoginContract
import java.lang.Thread.sleep

class LoginPresenter: LoginContract.Presenter {
    private var view: LoginContract.View? = null
    private val uiHandler = Handler(Looper.getMainLooper())

    override fun onAttach(view: LoginContract.View) {
        this.view = view
    }

    override fun onLogin(login: String, password: String) {
        view?.showProgress()
        Thread {
            sleep(1_000)
            uiHandler.post { // работа с вьюшками должна осуществляться из главного потока
                view?.hideProgress()
                if (checkCredentials(login, password)) {
                    view?.setSuccess()
                } else {
                    view?.setError("пароль не равен логину")
                }
            }
        }.start()
    }

    private fun checkCredentials(login: String, password: String): Boolean {
        return login == password
    }

    override fun onCredentialsChanged() {
        TODO("Not yet implemented")
    }
}