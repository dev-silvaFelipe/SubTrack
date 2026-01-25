package com.example.subtrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.Locale

class AssinaturaAdapter(private val assinaturas: List<Assinatura>) :
    RecyclerView.Adapter<AssinaturaAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNome: TextView = itemView.findViewById(R.id.tvNome)
        val tvValor: TextView = itemView.findViewById(R.id.tvValor)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // O layout agora é mais complexo (CardView), mas o inflate continua igual
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_assinatura, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = assinaturas[position]

        holder.tvNome.text = item.nome
        holder.tvValor.text = String.format(Locale("pt", "BR"), "R$ %.2f", item.valor)

        holder.itemView.setOnClickListener {
            val context = holder.itemView.context
            val intent = android.content.Intent(context, DetalhesActivity::class.java)
            intent.putExtra("ASSINATURA_ID", item.id)
            intent.putExtra("ASSINATURA_NOME", item.nome)
            intent.putExtra("ASSINATURA_VALOR", item.valor)
            intent.putExtra("USER_ID", item.usuarioId)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = assinaturas.size
}