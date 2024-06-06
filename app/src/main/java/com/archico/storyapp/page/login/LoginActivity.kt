package com.archico.storyapp.page.login

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.archico.storyapp.R
import com.archico.storyapp.data.ResultState
import com.archico.storyapp.databinding.ActivityLoginBinding
import com.archico.storyapp.page.ViewModelFactory
import com.archico.storyapp.page.home.MainActivity
import com.archico.storyapp.page.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var loginBinding: ActivityLoginBinding
    private val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val loginViewModel: LoginViewModel by viewModels<LoginViewModel> {
        viewModelFactory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        loginBinding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(loginBinding.root)
        supportActionBar?.hide()

        loginBinding.apply {
            hyplRegister.setOnClickListener{
                val registerActivity = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(registerActivity)
            }

            btnLogin.setOnClickListener {
                if(edLoginEmail.text!!.isNotEmpty() && edLoginPassword.text?.length!! >= 8 ){
                    loginViewModel.postLogin(
                        email = edLoginEmail.text.toString(),
                        password = edLoginPassword.text.toString()
                    )
                }else{
                    Toast.makeText(this@LoginActivity, "Invalid form", Toast.LENGTH_SHORT).show()
                }
            }

            val builder: AlertDialog.Builder = AlertDialog.Builder(this@LoginActivity)
            builder.setView(R.layout.dialog_loading)
            val dialog: AlertDialog = builder.create()

            loginViewModel.responseResult.observe(this@LoginActivity) { response ->
                when (response) {
                    is ResultState.Loading -> dialog.show()
                    is ResultState.Error -> {
                        dialog.dismiss()
                        Toast.makeText(
                            this@LoginActivity,
                            response.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ResultState.Success -> {
                        dialog.dismiss()
                        val mainActivity = Intent(this@LoginActivity, MainActivity::class.java)
                        startActivity(mainActivity)
                        finish()
                    }
                    else -> dialog.dismiss()
                }
            }
        }
        startAnimation()
    }

    private fun startAnimation() {
        ObjectAnimator.ofFloat(loginBinding.logo, View.TRANSLATION_X, -32f, 32f).apply {
            duration = 6400
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }
}



