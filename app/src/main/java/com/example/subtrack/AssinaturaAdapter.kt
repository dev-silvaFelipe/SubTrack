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
    }

    override fun getItemCount() = assinaturas.size
}