package com.example.subtrack

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class EditarActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBhelper
    private var assinaturaId: Int = 0
    private var usuarioId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_editar)

        dbHelper = DBhelper(this)

        val etNome = findViewById<EditText>(R.id.etEditarNome)
        val etValor = findViewById<EditText>(R.id.etEditarValor)
        val btnSalvar = findViewById<Button>(R.id.btnSalvarEdicao)

        assinaturaId = intent.getIntExtra("ASSINATURA_ID", 0)

        if (assinaturaId > 0) {
            val assinatura = dbHelper.obterAssinatura(assinaturaId)
            if (assinatura != null) {
                etNome.setText(assinatura.nome)
                etValor.setText(assinatura.valor.toString())
                usuarioId = assinatura.usuarioId
            }
        }

        btnSalvar.setOnClickListener {
            val novoNome = etNome.text.toString()
            val novoValorStr = etValor.text.toString()

            if (novoNome.isNotEmpty() && novoValorStr.isNotEmpty()) {
                val novoValor = novoValorStr.toDoubleOrNull() ?: 0.0

                val assinaturaEditada = Assinatura(assinaturaId, novoNome, novoValor, usuarioId)

                dbHelper.atualizarAssinatura(assinaturaEditada)
                Toast.makeText(this, "Atualizado com sucesso", Toast.LENGTH_SHORT).show()
                finish()
            }
        }
    }
}