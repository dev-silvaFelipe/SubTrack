package com.example.subtrack

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CadastroActivity : AppCompatActivity() {

    private var usuarioId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        usuarioId = intent.getIntExtra("USER_ID", 0)

        val etNome = findViewById<EditText>(R.id.etNomeAssinatura)
        val etValor = findViewById<EditText>(R.id.etValorAssinatura)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)

        btnSalvar.setOnClickListener {
            val nome = etNome.text.toString()
            val valorStr = etValor.text.toString()

            if (nome.isNotEmpty() && valorStr.isNotEmpty()) {
                val valor = valorStr.toDoubleOrNull() ?: 0.0

                val novaAssinatura = Assinatura(
                    nome = nome,
                    valor = valor,
                    usuarioId = usuarioId
                )

                salvarNaApi(novaAssinatura)

            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun salvarNaApi(assinatura: Assinatura) {
        RetrofitClient.instance.salvarAssinatura(assinatura).enqueue(object : Callback<Assinatura> {
            override fun onResponse(call: Call<Assinatura>, response: Response<Assinatura>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "Salvo na nuvem!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Erro no servidor: ${response.code()}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Assinatura>, t: Throwable) {
                Toast.makeText(applicationContext, "Falha de conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}