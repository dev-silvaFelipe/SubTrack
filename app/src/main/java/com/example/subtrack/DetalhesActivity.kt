package com.example.subtrack

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetalhesActivity : AppCompatActivity() {

    private var assinaturaId: Int = 0
    private var nome: String? = null
    private var valor: Double = 0.0
    private var userId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        val tvNome = findViewById<TextView>(R.id.tvDetalheNome)
        val tvValor = findViewById<TextView>(R.id.tvDetalheValor)
        val btnEditar = findViewById<Button>(R.id.btnEditar)
        val btnExcluir = findViewById<Button>(R.id.btnExcluir)

        assinaturaId = intent.getIntExtra("ASSINATURA_ID", 0)
        nome = intent.getStringExtra("ASSINATURA_NOME")
        valor = intent.getDoubleExtra("ASSINATURA_VALOR", 0.0)
        userId = intent.getIntExtra("USER_ID", 0)

        tvNome.text = nome
        tvValor.text = "R$ ${String.format("%.2f", valor)}"

        btnEditar.setOnClickListener {
            val intent = Intent(this, EditarActivity::class.java)
            intent.putExtra("ASSINATURA_ID", assinaturaId)
            intent.putExtra("ASSINATURA_NOME", nome)
            intent.putExtra("ASSINATURA_VALOR", valor)
            intent.putExtra("USER_ID", userId)
            startActivity(intent)
            finish()
        }

        btnExcluir.setOnClickListener {
            confirmarExclusao()
        }
    }

    private fun confirmarExclusao() {
        if (assinaturaId > 0) {
            RetrofitClient.instance.deletarAssinatura(assinaturaId).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        Toast.makeText(applicationContext, "Excluído com sucesso!", Toast.LENGTH_SHORT).show()
                        finish() // Volta para a lista
                    } else {
                        Toast.makeText(applicationContext, "Erro ao excluir", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(applicationContext, "Erro de rede: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }
}