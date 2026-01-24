package com.example.subtrack

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class CadastroActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBhelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro)

        dbHelper = DBhelper(this)

        val etNome = findViewById<EditText>(R.id.etNomeAssinatura)
        val etValor = findViewById<EditText>(R.id.etValorAssinatura)
        val btnSalvar = findViewById<Button>(R.id.btnSalvar)

        btnSalvar.setOnClickListener {
            val nome = etNome.text.toString()
            val valorStr = etValor.text.toString()

            if (nome.isNotEmpty() && valorStr.isNotEmpty()) {
                val valor = valorStr.toDoubleOrNull() ?: 0.0

                val novaAssinatura = Assinatura(nome = nome, valor = valor, usuarioId = 1)

                val resultado = dbHelper.inserirAssinatura(novaAssinatura)

                if (resultado > 0) {
                    Toast.makeText(this, "Salvo com sucesso!", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Erro ao salvar", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Preencha todos os campos", Toast.LENGTH_SHORT).show()
            }
        }
    }
}