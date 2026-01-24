package com.example.subtrack

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {

    private lateinit var dbHelper: DBhelper
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AssinaturaAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        dbHelper = DBhelper(this)
        recyclerView = findViewById(R.id.recyclerView)
        val fab = findViewById<FloatingActionButton>(R.id.fabAdd)

        recyclerView.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
        }
        fab.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        carregarLista()
    }

    private fun carregarLista() {
        val lista = dbHelper.listarAssinaturas(1)
        adapter = AssinaturaAdapter(lista)
        recyclerView.adapter = adapter
    }

}