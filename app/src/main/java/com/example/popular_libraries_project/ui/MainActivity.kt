package com.example.popular_libraries_project.ui

import android.app.Activity
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.annotation.MainThread
import androidx.core.view.isVisible
import com.example.popular_libraries_project.databinding.ActivityMainBinding
import com.example.popular_libraries_project.presenter.LoginPresenter
import com.example.popular_libraries_project.data.InMemoryUserRepositoryImpl
import com.example.popular_libraries_project.ui.LoginContract

class MainActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var binding: ActivityMainBinding
    private var presenter: LoginContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = restorePresenter()
        presenter?.onAttach(this)
        initHandlers()
    }

    @MainThread
    override fun setSuccess() {
        binding.authorizationGroup.isVisible = false
        binding.login.text.clear()
        binding.password.text.clear()
        binding.logoutGroup.isVisible = true
        binding.root.setBackgroundColor(Color.GREEN)
    }

    @MainThread
    override fun setError(error: String) {
        Toast.makeText(this, "ERROR: $error", Toast.LENGTH_LONG).show()
    }

    @MainThread
    override fun setErrorForgotPassword(error: String) {
        binding.includedLoadingLayout.loadingLayout.isVisible = false
        binding.sendForgotPasswordGroup.isVisible = true
        Toast.makeText(this, "ERROR: $error", Toast.LENGTH_LONG).show()
    }

    @MainThread
    override fun setSuccessRegistration(text: String) {
        binding.login.text.clear()
        binding.password.text.clear()
        Toast.makeText(this, "SUCCESS: $text", Toast.LENGTH_LONG).show()
    }

    @MainThread
    override fun setSuccessForgot(text: String) {
        binding.email.text.clear()
        binding.authorizationGroup.isVisible = true
        binding.includedLoadingLayout.loadingLayout.isVisible = false
        Toast.makeText(this, "SUCCESS: $text", Toast.LENGTH_LONG).show()
    }

    @MainThread
    override fun showProgress() {
        binding.authorizationGroup.isVisible = false
        binding.logoutGroup.isVisible = false
        binding.sendForgotPasswordGroup.isVisible = false
        binding.includedLoadingLayout.loadingLayout.isVisible = true
        hideKeyboard(this)
    }

    @MainThread
    override fun hideProgress() {
        binding.authorizationGroup.isVisible = true
        binding.loginButton.isEnabled = true
        binding.includedLoadingLayout.loadingLayout.isVisible = false
    }

    @MainThread
    override fun setLogout() {
        binding.root.setBackgroundColor(Color.WHITE)
        binding.logoutGroup.isVisible = false
        binding.authorizationGroup.isVisible = true
    }

    override fun setForgotPassword() {
        binding.authorizationGroup.isVisible = false
        binding.login.text.clear()
        binding.password.text.clear()
        binding.sendForgotPasswordGroup.isVisible = true
        binding.includedLoadingLayout.loadingLayout.isVisible = false
    }

    private fun restorePresenter(): LoginPresenter {
        val presenter = lastNonConfigurationInstance as? LoginPresenter
        val repository = InMemoryUserRepositoryImpl.instance
        return presenter ?: LoginPresenter(repository)
    }

    // в этом методе хроним только презентор, вью модель. Не нужно пихать всё подряд. Если что-то
    // еще должно пережить поворот экрана, то пускай переживает это в презенторе
    // для фрагмента - ретэйн фрагмент. Об этом позже
    override fun onRetainCustomNonConfigurationInstance(): Any? {
        return presenter
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
            presenter?.onLogin(binding.login.text.toString(), binding.password.text.toString())
        }
        binding.registrationButton.setOnClickListener {
            presenter?.onRegistration(binding.login.text.toString(), binding.password.text.toString())
        }
        binding.forgotPasswordButton.setOnClickListener {
            presenter?.onForgotPassword()
        }
        binding.sendForgotPasswordButton.setOnClickListener {
            presenter?.onSendForgotPassword(binding.email.text.toString());
        }
        binding.logoutButton.setOnClickListener {
            presenter?.onLogout()
        }
    }
}