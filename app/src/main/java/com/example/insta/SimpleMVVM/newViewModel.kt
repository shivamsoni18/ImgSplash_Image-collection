package com.example.insta.SimpleMVVM

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import com.example.insta.Api
import com.example.insta.ImageDataPojo
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class newViewModel() : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    internal var context: Context? = null

    //this is the data that we will fetch asynchronously
    private var ImageList: MutableLiveData<List<newPojo>>? = null


    fun getImageData2(): LiveData<List<newPojo>> {
        //if the list is null
        if (ImageList == null) {
            ImageList = MutableLiveData<List<newPojo>>()
            //we will load it asynchronously from server in this method
            loadImages2()
        }

        //finally we will return the list
        return ImageList as MutableLiveData<List<newPojo>>
    }

    private fun loadImages2() {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(Api::class.java)

        val call = api.getImageListNew()

        call.enqueue(object : Callback<List<newPojo>> {
            override fun onResponse(call: Call<List<newPojo>>, response: Response<List<newPojo>>) {
                //finally we are setting the list to our MutableLiveData
                ImageList?.setValue(response.body())
            }

            override fun onFailure(call: Call<List<newPojo>>, t: Throwable) {

            }
        })
    }
}