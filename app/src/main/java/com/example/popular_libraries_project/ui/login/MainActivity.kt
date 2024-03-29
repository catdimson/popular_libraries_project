package com.example.popular_libraries_project.ui.login

import android.app.Activity
import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.popular_libraries_project.app
import com.example.popular_libraries_project.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var viewModel: LoginContract.ViewModel? = null
    private val handler: Handler by lazy {
        Handler(Looper.getMainLooper())
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewModel = restoreViewModel()
        initHandlers()
        initSubscribers()
    }

    private fun setSuccess() {
        binding.authorizationGroup.isVisible = false
        binding.login.text.clear()
        binding.password.text.clear()
        binding.logoutGroup.isVisible = true
        binding.root.setBackgroundColor(Color.GREEN)
    }

    private fun showError(error: String?) {
        Toast.makeText(this, "ERROR: $error", Toast.LENGTH_LONG).show()
    }

    private fun showSuccess(success: String?) {
        Toast.makeText(this, "SUCCESS: $success", Toast.LENGTH_LONG).show()
    }

    private fun setErrorForgotPassword(error: String) {
        binding.includedLoadingLayout.loadingLayout.isVisible = false
        binding.sendForgotPasswordGroup.isVisible = true
        showError(error)
    }

    private fun setSuccessRegistration(text: String) {
        binding.login.text.clear()
        binding.password.text.clear()
        showSuccess(text)
    }

    private fun setSuccessForgotPassword(text: String) {
        binding.email.text.clear()
        binding.authorizationGroup.isVisible = true
        binding.includedLoadingLayout.loadingLayout.isVisible = false
        binding.sendForgotPasswordGroup.isVisible = false
        showSuccess(text)
    }

    private fun showProgress() {
        binding.authorizationGroup.isVisible = false
        binding.logoutGroup.isVisible = false
        binding.sendForgotPasswordGroup.isVisible = false
        binding.includedLoadingLayout.loadingLayout.isVisible = true
        hideKeyboard(this)
    }

    private fun hideProgress() {
        binding.authorizationGroup.isVisible = true
        binding.loginButton.isEnabled = true
        binding.includedLoadingLayout.loadingLayout.isVisible = false
    }

    private fun setLogout() {
        binding.root.setBackgroundColor(Color.WHITE)
        binding.logoutGroup.isVisible = false
        binding.authorizationGroup.isVisible = true
    }

    private fun setForgotPassword() {
        binding.authorizationGroup.isVisible = false
        binding.login.text.clear()
        binding.password.text.clear()
        binding.sendForgotPasswordGroup.isVisible = true
        binding.includedLoadingLayout.loadingLayout.isVisible = false
    }

    private fun restoreViewModel(): LoginViewModel {
        val viewModel = lastCustomNonConfigurationInstance as? LoginViewModel
        return viewModel ?: LoginViewModel(
            app.loginUsecase,
            app.logoutUsecase,
            app.registerUsecase,
            app.forgotPasswordUsecase
        )
    }

    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return viewModel
    }

    private fun hideKeyboard(activity: Activity) {
        val imm: InputMethodManager = activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = activity.currentFocus
        if (view == null) {
            view = View(activity)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun initHandlers() {
        binding.loginButton.setOnClickListener {
            viewModel?.onLogin(binding.login.text.toString(), binding.password.text.toString())
        }
        binding.registrationButton.setOnClickListener {
            viewModel?.onRegistration(binding.login.text.toString(), binding.password.text.toString())
        }
        binding.forgotPasswordButton.setOnClickListener {
            viewModel?.onForgotPassword()
        }
        binding.sendForgotPasswordButton.setOnClickListener {
            viewModel?.onSendForgotPassword(binding.email.text.toString());
        }
        binding.logoutButton.setOnClickListener {
            viewModel?.onLogout()
        }
    }

    private fun initSubscribers() {
        initShouldShowProgressSubscribe()
        initLogoutSubscribe()
        initErrorTextSubscribe()
        initShowForgotPasswordElementsSubscribe()
        initShowSuccessInput()
        initRegistrationSubscribe()
        initSendForgotPasswordSubscribe()
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel?.isSuccess?.unsubscribeAll()
        viewModel?.errorText?.unsubscribeAll()
        viewModel?.shouldShowProgress?.unsubscribeAll()
        viewModel?.isLogout?.unsubscribeAll()
        viewModel?.showForgotPassword?.unsubscribeAll()
        viewModel?.registration?.unsubscribeAll()
        viewModel?.sendForgotPassword?.unsubscribeAll()
    }

    private fun initShouldShowProgressSubscribe() {
        viewModel?.shouldShowProgress?.subscribe(handler) { shouldShow ->
            if (shouldShow == true) {
                showProgress()
            } else {
                hideProgress()
            }
        }
    }

    private fun initLogoutSubscribe() {
        viewModel?.isLogout?.subscribe(handler) { isLogout ->
            if (isLogout == true) {
                setLogout()
            }
        }
    }

    private fun initErrorTextSubscribe() {
        viewModel?.errorText?.subscribe(handler) { error ->
            val success = viewModel?.isSuccess?.value
            if (success == false) {
                showError(error)
            }
        }
    }

    private fun initShowForgotPasswordElementsSubscribe() {
        viewModel?.showForgotPassword?.subscribe(handler) { isShowForgotPassword ->
            if (isShowForgotPassword == true) {
                setForgotPassword()
            }
        }
    }

    private fun initShowSuccessInput() {
        viewModel?.isSuccess?.subscribe(handler) { isSuccessInput ->
            if (isSuccessInput == true) {
                setSuccess()
            }
        }
    }

    private fun initRegistrationSubscribe() {
        viewModel?.registration?.subscribe(handler) { isRegistration ->
            if (isRegistration == true) {
                setSuccessRegistration("Пользователь зарегистрирован")
            }
        }
    }

    private fun initSendForgotPasswordSubscribe() {
        viewModel?.sendForgotPassword?.subscribe(handler) { isSendMessage ->
            if (isSendMessage == true) {
                setSuccessForgotPassword("Сообщение отправлено на почту")
            } else {
                setErrorForgotPassword("Не удачно. Попробуйте еще раз.")
            }
        }
    }
}
