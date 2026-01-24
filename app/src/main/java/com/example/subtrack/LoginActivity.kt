package com.example.subtrack

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val etUser = findViewById<EditText>(R.id.editUsername)
        val etPass = findViewById<EditText>(R.id.editPassword)
        val btnLogin = findViewById<Button>(R.id.buttonLogin)

        btnLogin.setOnClickListener {
            val user = etUser.text.toString()
            val pass = etPass.text.toString()

            if (user.isNotEmpty() && pass.isNotEmpty()) {
                fazerLoginRemoto(user, pass)
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun fazerLoginRemoto(user: String, pass: String) {

        val loginData = Usuario(username = user, password = pass)


        RetrofitClient.instance.fazerLogin(loginData).enqueue(object : Callback<Usuario> {
            override fun onResponse(call: Call<Usuario>, response: Response<Usuario>) {
                if (response.isSuccessful && response.body() != null) {

                    Toast.makeText(applicationContext, "Login via API realizado!", Toast.LENGTH_SHORT).show()

                    val usuarioLogado = response.body()!!

                    val intent = Intent(this@LoginActivity, MainActivity::class.java)
                    intent.putExtra("USER_ID", usuarioLogado.id)
                    startActivity(intent)
                    finish()
                } else {

                    Toast.makeText(applicationContext, "Usuário ou senha inválidos", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Usuario>, t: Throwable) {

                Toast.makeText(applicationContext, "Erro de conexão: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }
}