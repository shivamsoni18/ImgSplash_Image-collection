package com.example.insta

import com.example.insta.SimpleMVVM.newPojo
import retrofit2.Call
import retrofit2.http.GET

interface Api {

    @GET("list")
    fun getImageList(): Call<List<ImageDataPojo>>

    @GET("list")
    fun getImageListNew(): Call<List<newPojo>>
}