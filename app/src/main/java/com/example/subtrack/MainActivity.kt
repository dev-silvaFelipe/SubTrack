package com.example.subtrack

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: AssinaturaAdapter
    private var usuarioId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usuarioId = intent.getIntExtra("USER_ID", 0)

        recyclerView = findViewById(R.id.recyclerView)
        val fab = findViewById<FloatingActionButton>(R.id.fabAdd)

        recyclerView.layoutManager = LinearLayoutManager(this)

        fab.setOnClickListener {
            val intent = Intent(this, CadastroActivity::class.java)
            intent.putExtra("USER_ID", usuarioId)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        carregarListaDaApi()
    }

    private fun carregarListaDaApi() {
        RetrofitClient.instance.listarAssinaturas().enqueue(object : Callback<List<Assinatura>> {
            override fun onResponse(call: Call<List<Assinatura>>, response: Response<List<Assinatura>>) {
                if (response.isSuccessful && response.body() != null) {
                    val listaTodas = response.body()!!
                    val listaFiltrada = listaTodas.filter { it.usuarioId == usuarioId }

                    adapter = AssinaturaAdapter(listaFiltrada)
                    recyclerView.adapter = adapter
                } else {
                    Toast.makeText(applicationContext, "Erro ao carregar lista", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<List<Assinatura>>, t: Throwable) {
                Toast.makeText(applicationContext, "Sem conexão: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}