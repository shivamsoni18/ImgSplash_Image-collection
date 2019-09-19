package com.example.insta.SimpleMVVM

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName



class newPojo {

    @SerializedName("format")
    @Expose
    private var format: String? = null
    @SerializedName("width")
    @Expose
    private var width: Int? = null
    @SerializedName("height")
    @Expose
    private var height: Int? = null
    @SerializedName("filename")
    @Expose
    private var filename: String? = null
    @SerializedName("id")
    @Expose
    private var id: Int? = null
    @SerializedName("author")
    @Expose
    private var author: String? = null
    @SerializedName("author_url")
    @Expose
    private var authorUrl: String? = null
    @SerializedName("post_url")
    @Expose
    private var postUrl: String? = null

    fun getFormat(): String? {
        return format
    }

    fun setFormat(format: String) {
        this.format = format
    }

    fun getWidth(): Int? {
        return width
    }

    fun setWidth(width: Int?) {
        this.width = width
    }

    fun getHeight(): Int? {
        return height
    }

    fun setHeight(height: Int?) {
        this.height = height
    }

    fun getFilename(): String? {
        return filename
    }

    fun setFilename(filename: String) {
        this.filename = filename
    }

    fun getId(): Int? {
        return id
    }

    fun setId(id: Int?) {
        this.id = id
    }

    fun getAuthor(): String? {
        return author
    }

    fun setAuthor(author: String) {
        this.author = author
    }

    fun getAuthorUrl(): String? {
        return authorUrl
    }

    fun setAuthorUrl(authorUrl: String) {
        this.authorUrl = authorUrl
    }

    fun getPostUrl(): String? {
        return postUrl
    }

    fun setPostUrl(postUrl: String) {
        this.postUrl = postUrl
    }

}