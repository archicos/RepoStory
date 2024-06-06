package com.archico.storyapp.page.register

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.archico.storyapp.R
import com.archico.storyapp.data.ResultState
import com.archico.storyapp.databinding.ActivityRegisterBinding
import com.archico.storyapp.page.ViewModelFactory
import com.archico.storyapp.page.login.LoginActivity

class RegisterActivity : AppCompatActivity(){
    private lateinit var registerBinding: ActivityRegisterBinding
    private val viewModelFactory: ViewModelFactory = ViewModelFactory.getInstance(this)
    private val registerViewModel:RegisterViewModel by viewModels<RegisterViewModel>{
        viewModelFactory
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        registerBinding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(registerBinding.root)
        supportActionBar?.hide()

        val loginActivity = Intent(this, LoginActivity::class.java)
        registerBinding.apply {
            hyplLogin.setOnClickListener{
                startActivity(loginActivity)
            }
            btnRegister.setOnClickListener {
                if (etRegisterEmail.text!!.isNotEmpty() && etRegisterUsername.text!!.isNotEmpty() && edRegisterPassword.text?.length!! >= 8){
                    registerViewModel.postRegister(
                        name = etRegisterUsername.text.toString(),
                        email = etRegisterEmail.text.toString(),
                        password = edRegisterPassword.text.toString()
                    )
                }else{
                    Toast.makeText(this@RegisterActivity, "Invalid form fill", Toast.LENGTH_SHORT).show()
                }
            }

            val builder: AlertDialog.Builder = AlertDialog.Builder(this@RegisterActivity)
            builder.setView(R.layout.dialog_loading)
            val dialog: AlertDialog = builder.create()

            registerViewModel.responseResult.observe(this@RegisterActivity) {
                response ->
                when (response) {
                    is ResultState.Loading -> dialog.show()
                    is ResultState.Error -> {
                        dialog.dismiss()
                        Toast.makeText(
                            this@RegisterActivity,
                            response.error,
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    is ResultState.Success -> {
                        dialog.dismiss()
                        val homeActivity = Intent(this@RegisterActivity, LoginActivity::class.java)
                        startActivity(homeActivity)
                        finish()
                    }
                    else -> dialog.dismiss()
                }
            }
        }
    }
}