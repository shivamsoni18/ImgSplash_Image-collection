package com.example.insta

import android.annotation.SuppressLint
import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import android.arch.lifecycle.ViewModel
import android.content.Context
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ImageListViewModel() : ViewModel() {

    @SuppressLint("StaticFieldLeak")
    internal var context: Context? = null

    //this is the data that we will fetch asynchronously
    private var ImageList: MutableLiveData<List<ImageDataPojo>>? = null

    //we will call this method to get the data
    fun getImageData(): LiveData<List<ImageDataPojo>> {
        //if the list is null
        if (ImageList == null) {
            ImageList = MutableLiveData<List<ImageDataPojo>>()
            //we will load it asynchronously from server in this method
            loadImages()
        }

        //finally we will return the list
        return ImageList as MutableLiveData<List<ImageDataPojo>>
    }

    private fun loadImages() {

        val retrofit = Retrofit.Builder()
            .baseUrl("https://picsum.photos/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retrofit.create(Api::class.java)

        val call = api.getImageList()


        call.enqueue(object : Callback<List<ImageDataPojo>> {
            override fun onResponse(call: Call<List<ImageDataPojo>>, response: Response<List<ImageDataPojo>>) {
                //finally we are setting the list to our MutableLiveData
                ImageList?.setValue(response.body())
            }

            override fun onFailure(call: Call<List<ImageDataPojo>>, t: Throwable) {

            }
        })
    }

}