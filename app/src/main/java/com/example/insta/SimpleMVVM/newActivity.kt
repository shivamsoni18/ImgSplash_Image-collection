package com.example.insta.SimpleMVVM

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import android.widget.LinearLayout
import com.example.insta.ImageListAdapter
import com.example.insta.ImageListViewModel
import com.example.insta.R
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_new.*
import java.util.*

class newActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new)

        recyclerviewImageListNew.setLayoutManager(LinearLayoutManager(this, LinearLayout.VERTICAL, false))
        getDataList()
    }

    fun getDataList() {

        val model2 = ViewModelProviders.of(this).get(newViewModel::class.java!!)

        model2.getImageData2().observe(this, object : Observer<List<newPojo>> {
            override fun onChanged(t: List<newPojo>?) {
//                Collections.shuffle(t)
                recyclerviewImageListNew.adapter = t?.let {
                    newAdapter(this@newActivity, it)
                }
            }
        })
    }
}
