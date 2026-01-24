package com.example.subtrack

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {

    @POST("login")
    fun fazerLogin(@Body loginData: Usuario): Call<Usuario>

    @GET("subscriptions")
    fun listarAssinaturas(): Call<List<Assinatura>>

    @POST("subscriptions")
    fun salvarAssinatura(@Body assinatura: Assinatura): Call<Assinatura>

    @PUT("subscriptions/{id}")
    fun atualizarAssinatura(@Path("id") id: Int, @Body assinatura: Assinatura): Call<Void>

    @DELETE("subscriptions/{id}")
    fun deletarAssinatura(@Path("id") id: Int): Call<Void>

    @GET("subscriptions/{id}")
    fun obterAssinatura(@Path("id") id: Int): Call<Assinatura>
}