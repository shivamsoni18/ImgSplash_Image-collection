@file:Suppress("DEPRECATED_IDENTITY_EQUALS")

package com.example.insta

import android.Manifest
import android.app.ProgressDialog
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.StaggeredGridLayoutManager
import android.view.View
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*


class MainActivity : AppCompatActivity() {

    internal lateinit var progress: ProgressDialog


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mainloader.visibility = View.VISIBLE

        /* progress = ProgressDialog(this)
         progress.setMessage("Loading...")
         progress.setCancelable(false)
         progress.show()*/


        recyclerviewImageList.setLayoutManager(LinearLayoutManager(this, LinearLayout.VERTICAL, false))


        CheckPermission()

        getDataList()

        shuffle.setOnClickListener(View.OnClickListener {
            listView()
        })

        gridlay.setOnClickListener(View.OnClickListener {
            gridView()
        })

        listlay.setOnClickListener(View.OnClickListener {
            listView()
        })

    }


    private fun listView() {

        recyclerviewImageList.setLayoutManager(LinearLayoutManager(this, LinearLayout.VERTICAL, false))
        getDataList()
    }

    private fun gridView() {

        recyclerviewImageList.setLayoutManager(
            StaggeredGridLayoutManager(
                2,
                StaggeredGridLayoutManager.VERTICAL
            )
        )
        getDataGrid()
    }

    fun CheckPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && checkSelfPermission(
                Manifest.permission.READ_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED && checkSelfPermission(
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) !== PackageManager.PERMISSION_GRANTED
        ) {
            requestPermissions(
                arrayOf(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ), 0
            )

        } else {

        }
    }

    fun getDataList() {

        gridlay.visibility = View.VISIBLE
        listlay.visibility = View.GONE

        val model = ViewModelProviders.of(this).get(ImageListViewModel::class.java!!)

        model.getImageData().observe(this, object : Observer<List<ImageDataPojo>> {
            override fun onChanged(t: List<ImageDataPojo>?) {
                Collections.shuffle(t)
                recyclerviewImageList.adapter = t?.let { ImageListAdapter(this@MainActivity, it) }
//                progress.dismiss()
                mainloader.visibility = View.GONE


            }

        })
    }

    fun getDataGrid() {

        gridlay.visibility = View.GONE
        listlay.visibility = View.VISIBLE

        val model = ViewModelProviders.of(this).get(ImageListViewModel::class.java!!)

        model.getImageData().observe(this, object : Observer<List<ImageDataPojo>> {
            override fun onChanged(t: List<ImageDataPojo>?) {
                Collections.shuffle(t)
                recyclerviewImageList.adapter = t?.let {
                    ImageListAdapterGrid(this@MainActivity, it)
                }
//                progress.dismiss()
                mainloader.visibility = View.GONE


            }
        })
    }
}
