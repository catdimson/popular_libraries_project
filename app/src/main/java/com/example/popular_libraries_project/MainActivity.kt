package com.example.popular_libraries_project

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
import com.example.popular_libraries_project.view.LoginContract

class MainActivity : AppCompatActivity(), LoginContract.View {
    private lateinit var binding: ActivityMainBinding
    private var presenter: LoginContract.Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        presenter = restorePresenter()
        presenter?.onAttach(this)

        binding.loginButton.setOnClickListener {
            presenter?.onLogin(binding.login.text.toString(), binding.password.text.toString())
        }
    }

    @MainThread
    override fun setSuccess() {
        binding.authorizationGroup.isVisible = false
        binding.root.setBackgroundColor(Color.GREEN)
    }

    @MainThread
    override fun setError(error: String) {
        Toast.makeText(this, "ERROR: $error", Toast.LENGTH_LONG).show()
    }

    @MainThread
    override fun showProgress() {
        binding.loginButton.isEnabled = false
        binding.includedLoadingLayout.loadingLayout.isVisible = true
        hideKeyboard(this)
    }

    @MainThread
    override fun hideProgress() {
        binding.loginButton.isEnabled = true
        binding.includedLoadingLayout.loadingLayout.isVisible = false
    }

    private fun restorePresenter(): LoginPresenter {
        val presenter = lastNonConfigurationInstance as? LoginPresenter
        return presenter ?: LoginPresenter()
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
}