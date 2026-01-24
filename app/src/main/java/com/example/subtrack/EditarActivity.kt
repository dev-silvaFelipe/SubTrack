package com.example.subtrack

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditarActivity : AppCompatActivity() {

    private var assinaturaId: Int = 0
    private var usuarioId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar)

        val etNome = findViewById<EditText>(R.id.etEditarNome)
        val etValor = findViewById<EditText>(R.id.etEditarValor)
        val btnSalvar = findViewById<Button>(R.id.btnSalvarEdicao)

        assinaturaId = intent.getIntExtra("ASSINATURA_ID", 0)
        usuarioId = intent.getIntExtra("USER_ID", 0)
        val nomeAtual = intent.getStringExtra("ASSINATURA_NOME")
        val valorAtual = intent.getDoubleExtra("ASSINATURA_VALOR", 0.0)


        etNome.setText(nomeAtual)
        etValor.setText(valorAtual.toString())

        btnSalvar.setOnClickListener {
            val novoNome = etNome.text.toString()
            val novoValorStr = etValor.text.toString()

            if (novoNome.isNotEmpty() && novoValorStr.isNotEmpty()) {
                val novoValor = novoValorStr.toDoubleOrNull() ?: 0.0


                val assinaturaEditada = Assinatura(assinaturaId, novoNome, novoValor, usuarioId)

                salvarAlteracao(assinaturaId, assinaturaEditada)
            }
        }
    }

    private fun salvarAlteracao(id: Int, assinatura: Assinatura) {
        RetrofitClient.instance.atualizarAssinatura(id, assinatura).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(applicationContext, "Atualizado com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(applicationContext, "Erro ao atualizar", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(applicationContext, "Erro de rede: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}