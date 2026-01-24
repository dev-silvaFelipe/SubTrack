package com.example.subtrack

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class AssinaturaAdapter(private val assinaturas: List<Assinatura>) :
    RecyclerView.Adapter<AssinaturaAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val tvNome: TextView = view.findViewById(R.id.tvNome)
        val tvValor: TextView = view.findViewById(R.id.tvValor)
        val tvVencimento: TextView = view.findViewById(R.id.tvVencimento)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_assinatura, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = assinaturas[position]
        holder.tvNome.text = item.nome
        holder.tvValor.text = "R$ ${String.format("%.2f", item.valor)}"
        holder.tvVencimento.text = "ID: ${item.id}"

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

    override fun getItemCount() = assinaturas.size
}