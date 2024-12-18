package com.example.dwitchapp.service


import com.example.dwitchapp.model.OrdersResponse
import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.Date
import com.squareup.moshi.adapters.Rfc3339DateJsonAdapter
import retrofit2.http.GET
import retrofit2.http.Header


interface DwitchService {

    @GET("orders?populate=*")
    suspend fun getOrders(
        @Header("Authorization") token: String
    ): OrdersResponse
}


object ApiClient {
    private val moshi = Moshi.Builder()
        .add(Date::class.java, Rfc3339DateJsonAdapter())
        .build()


    private val retrofit = Retrofit.Builder()
        //... Les paramètres Retrofit ici (notamment un petit lien avec moshi)
        .baseUrl("https://dwitch.pickle-forge.app/api/")
        .addConverterFactory(MoshiConverterFactory.create(moshi))
        .build()


    // Puis créez votre service
    val dwitchService: DwitchService = retrofit.create(DwitchService::class.java)
}