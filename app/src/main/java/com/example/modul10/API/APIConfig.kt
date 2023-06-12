package com.example.modul10.API

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APIConfig {
    @JvmStatic
    val apiService: ApiService
        get() {
            val retrofit = Retrofit.Builder()
                .baseUrl("https://restapi.telenurse.web.id/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            return retrofit.create(ApiService::class.java)
        }
}