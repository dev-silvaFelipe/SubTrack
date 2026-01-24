package com.example.subtrack

import com.google.gson.annotations.SerializedName

data class Assinatura(
    val id: Int = 0,
    val nome: String,
    val valor: Double,
    @SerializedName(value = "usuarioId", alternate = ["usuario_id"])
    val usuarioId: Int
)