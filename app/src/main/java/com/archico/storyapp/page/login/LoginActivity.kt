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
import com.archico.storyapp.page.home.HomeActivity
import com.archico.storyapp.page.register.RegisterActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private val factory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val loginViewModel: LoginViewModel by viewModels<LoginViewModel> {
        factory
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.apply {
            hyperlinkRegister.setOnClickListener{
                val registerActivity = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(registerActivity)
            }

            loginButton.setOnClickListener {

                if(edLoginEmail.text!!.isNotEmpty() && edLoginPassword.text?.length!! >= 8 ){
                    loginViewModel.submitLogin(
                        email = edLoginEmail.text.toString(),
                        password = edLoginPassword.text.toString()
                    )
                }else{
                    Toast.makeText(this@LoginActivity, "Please fill the form correctly", Toast.LENGTH_SHORT).show()
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
                        val homeActivity = Intent(this@LoginActivity, HomeActivity::class.java)
                        startActivity(homeActivity)
                        finish()
                    }

                    else -> dialog.dismiss()
                }
            }
        }

        playAnimation()
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.logo, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()
    }
}



