package com.example.subtrack

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetalhesActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBhelper
    private var assinaturaId: Int = 0

    private lateinit var tvNome: TextView
    private lateinit var tvValor: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        dbHelper = DBhelper(this)

        tvNome = findViewById(R.id.tvDetalheNome)
        tvValor = findViewById(R.id.tvDetalheValor)
        val btnEditar = findViewById<Button>(R.id.btnEditar)
        val btnExcluir = findViewById<Button>(R.id.btnExcluir)

        assinaturaId = intent.getIntExtra("ASSINATURA_ID", 0)

        carregarDados()

        btnEditar.setOnClickListener {
            val intent = Intent(this, EditarActivity::class.java)
            intent.putExtra("ASSINATURA_ID", assinaturaId)
            startActivity(intent)
        }

        btnExcluir.setOnClickListener {
            if (assinaturaId > 0) {
                dbHelper.deletarAssinatura(assinaturaId)
                Toast.makeText(this, "Assinatura excluída", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        carregarDados()
    }

    private fun carregarDados() {
        if (assinaturaId > 0) {
            val assinatura = dbHelper.obterAssinatura(assinaturaId)
            if (assinatura != null) {
                tvNome.text = assinatura.nome
                tvValor.text = "R$ ${String.format("%.2f", assinatura.valor)}"
            } else {
                finish()
            }
        }
    }
}