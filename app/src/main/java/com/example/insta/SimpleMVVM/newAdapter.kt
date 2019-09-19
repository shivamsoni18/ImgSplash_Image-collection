package com.example.insta.SimpleMVVM

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import com.example.insta.ImageDataPojo
import com.example.insta.R
import kotlinx.android.synthetic.main.imagelist_raw.view.*

class newAdapter(private val context: Context, private var newPojoList: List<newPojo>) : RecyclerView.Adapter<newAdapter.newViewHolder>() {
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): newAdapter.newViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.imagelist_raw, p0, false)
        return newAdapter.newViewHolder(view)
    }

    override fun getItemCount(): Int {
        return newPojoList.size
    }

    override fun onBindViewHolder(p0: newAdapter.newViewHolder, p1: Int) {
        val newPojoList = newPojoList.get(p1)
        p0?.tvAuthorName?.text = newPojoList.getAuthor()
    }

    class newViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvAuthorName = itemView.author_name
    }
}