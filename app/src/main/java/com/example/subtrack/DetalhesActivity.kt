package com.example.subtrack

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class DetalhesActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBhelper
    private var assinaturaId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalhes)

        dbHelper = DBhelper(this)

        val tvNome = findViewById<TextView>(R.id.tvDetalheNome)
        val tvValor = findViewById<TextView>(R.id.tvDetalheValor)
        val btnExcluir = findViewById<Button>(R.id.btnExcluir)

        assinaturaId = intent.getIntExtra("ASSINATURA_ID", 0)

        if (assinaturaId > 0) {
            val assinatura = dbHelper.obterAssinatura(assinaturaId)
            if (assinatura != null) {
                tvNome.text = assinatura.nome
                tvValor.text = "R$ ${String.format("%.2f", assinatura.valor)}"
            }
        }

        btnExcluir.setOnClickListener {
            if (assinaturaId > 0) {
                dbHelper.deletarAssinatura(assinaturaId)
                Toast.makeText(this, "Assinatura excluída", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}