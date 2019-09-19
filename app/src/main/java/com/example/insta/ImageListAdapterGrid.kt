package com.example.insta

import android.app.Dialog
import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Environment
import android.support.v4.content.ContextCompat.startActivity
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.view.Window
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.otaliastudios.zoom.ZoomImageView
import kotlinx.android.synthetic.main.imagelist_raw.view.*


class ImageListAdapterGrid(private val context: Context, private var ImageDataPojoList: List<ImageDataPojo>) :
    RecyclerView.Adapter<ImageListAdapterGrid.ImageListViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageListViewHolder {


        val view = LayoutInflater.from(context).inflate(R.layout.imagelist_raw_hori, parent, false)
        return ImageListViewHolder(view)

    }

    private inner class HelloWebViewClient : WebViewClient() {
        override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
            return false
        }
    }

    override fun onBindViewHolder(holder: ImageListViewHolder, pos: Int) {

        val imageListPojo = ImageDataPojoList.get(pos)
        val width5 = imageListPojo.getWidth()?.div(10)
        val widtho5 = imageListPojo.getWidth()?.div(7)
        val width15 = imageListPojo.getWidth()?.div(15)
        val width2 = imageListPojo.getWidth()?.div(2)
        val height5 = imageListPojo.getHeight()?.div(10)
        val heighto5 = imageListPojo.getHeight()?.div(7)
        val height15 = imageListPojo.getHeight()?.div(15)
        val height2 = imageListPojo.getHeight()?.div(2)
        val width = imageListPojo.getWidth()
        val height = imageListPojo.getHeight()

        val requestOption = RequestOptions()
            .placeholder(R.drawable.imgplaceholder).centerCrop()
            .diskCacheStrategy(DiskCacheStrategy.ALL)


        if (widtho5 != null && heighto5 != null) {
            holder.Image.layoutParams.width= MATCH_PARENT
            holder.Image.layoutParams.height=heighto5
        }

        Glide
            .with(context)
            .load("https://picsum.photos/id/" + imageListPojo.getId() + "/" + width5 + "/" + height5 + "")
            .centerCrop()

            .thumbnail(
                Glide.with(context)
                    .load("https://picsum.photos/id/" + imageListPojo.getId() + "/" + width15 + "/" + height15 + "")
                    .centerCrop()
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
            )
            .apply(requestOption)
            .into(holder.Image)
//            .into(holder?.Image);




        holder?.tvAuthorName?.text = imageListPojo.getAuthor()
        holder?.tvAuthorUrl?.text = imageListPojo.getAuthorUrl()

        holder?.author_layout.setOnClickListener {
            /* val i = Intent(context, Main2Activity::class.java)
             i.putExtra("url", imageListPojo.getAuthorUrl())
             context.startActivity(i)*/

            /*val uri = Uri.parse(imageListPojo.getAuthorUrl()) // missing 'http://' will cause crashed
            val intent = Intent(Intent.ACTION_VIEW, uri)
            context.startActivity(intent)*/

            val MyDialogWeb = Dialog(context)
            MyDialogWeb.requestWindowFeature(Window.FEATURE_ACTION_BAR)
            MyDialogWeb.setContentView(R.layout.dialog_webview)
            MyDialogWeb.window.setBackgroundDrawableResource(android.R.color.transparent);
            val webviewDialog = MyDialogWeb.findViewById(R.id.webviewDialog) as WebView

            webviewDialog.getSettings().setJavaScriptEnabled(true)
            webviewDialog.getSettings().setLoadWithOverviewMode(true)
            webviewDialog.getSettings().setUseWideViewPort(true)
            webviewDialog.getSettings().setBuiltInZoomControls(true)
            webviewDialog.getSettings().setPluginState(WebSettings.PluginState.ON)
//        wb.getSettings().setPluginsEnabled(true);
            webviewDialog.setWebViewClient(HelloWebViewClient())
            webviewDialog.loadUrl(imageListPojo.getAuthorUrl())
            MyDialogWeb.show()
        }
        

        holder.mainDwnld.setOnClickListener {

            try {
                val downloadmanager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

                val uri =
                    Uri.parse("https://picsum.photos/id/" + imageListPojo.getId() + "/" + width + "/" + height + "")

                val request = DownloadManager.Request(uri)
                request.setTitle(imageListPojo.getFilename())
                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                request.setVisibleInDownloadsUi(true)
                request.setDestinationInExternalPublicDir(
                    Environment.DIRECTORY_DOWNLOADS,
                    "/Unsplash IMGs/" + "/" + imageListPojo.getFilename()
                );
                downloadmanager!!.enqueue(request)

                Toast.makeText(context, "Downloading.. ", Toast.LENGTH_LONG).show()
            } catch (s: SecurityException) {
                StoragePermissionRequest()
                s.printStackTrace()

            }


        }

        holder.mainShare.setOnClickListener {

            val i = Intent(Intent.ACTION_SEND)
            i.type = "text/plain"
            i.putExtra(Intent.EXTRA_SUBJECT, "Post by " + imageListPojo.getAuthor())
            i.putExtra(Intent.EXTRA_TEXT, imageListPojo.getPostUrl() + "\nPost by " + imageListPojo.getAuthor())
            startActivity(context, Intent.createChooser(i, "Post by " + imageListPojo.getAuthor()), null)

        }

        holder?.Image.setOnClickListener {

            val MyDialog = Dialog(context)
            MyDialog.requestWindowFeature(Window.FEATURE_ACTION_BAR)
            MyDialog.window.setBackgroundDrawableResource(android.R.color.transparent);
            MyDialog.setContentView(R.layout.customdialog_hori)
            val imgDialog = MyDialog.findViewById(R.id.imgDialog) as ZoomImageView
            val closeimage= MyDialog.findViewById(R.id.closebtn) as ImageView
            closeimage.setOnClickListener(){
                MyDialog.dismiss()
            }
//            val closeDialog = MyDialog.findViewById(R.id.closeDialog) as ImageView
//            val imgDialog = MyDialog.findViewById(R.id.imgDialog) as ImageView
            val imgDwnld = MyDialog.findViewById(R.id.mainDwnld) as ImageView
            val share = MyDialog.findViewById(R.id.mainShare) as ImageView
            val tvAuthorName = MyDialog.findViewById(R.id.author_name) as TextView
            val author_layout = MyDialog.findViewById(R.id.author_layout) as LinearLayout

           /* closeDialog.setOnClickListener {

                MyDialog.dismiss()

            }*/

//            val imgDialog = MyDialog.findViewById(R.id.imgDialog) as SubsamplingScaleImageView



            Glide
                .with(context)
                .load("https://picsum.photos/id/" + imageListPojo.getId() + "/" + width2 + "/" + height2 + "")
                .thumbnail(
                    Glide.with(context)
                        .load("https://picsum.photos/id/" + imageListPojo.getId() + "/" + width15 + "/" + height15 + "")
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                )
                .diskCacheStrategy(DiskCacheStrategy.NONE)
                .placeholder(R.drawable.prodplaceholder)
                .into(imgDialog);

             imgDwnld.setOnClickListener {

                 val downloadmanager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

                 val uri =
                     Uri.parse("https://picsum.photos/id/" + imageListPojo.getId() + "/" + width + "/" + height + "")

                 val request = DownloadManager.Request(uri)
                 request.setTitle(imageListPojo.getFilename())
                 request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
                 request.setVisibleInDownloadsUi(true)
                 request.setDestinationInExternalPublicDir(
                     Environment.DIRECTORY_DOWNLOADS,
                     "/Unsplash IMGs/" + "/" + imageListPojo.getFilename()
                 );
                 downloadmanager!!.enqueue(request)

                 Toast.makeText(context, "Downloading.. ", Toast.LENGTH_LONG).show()


             }

             share.setOnClickListener {

                 val i = Intent(Intent.ACTION_SEND)
                 i.type = "text/plain"
                 i.putExtra(Intent.EXTRA_SUBJECT, "Post by "+imageListPojo.getAuthor())
                 i.putExtra(Intent.EXTRA_TEXT, imageListPojo.getPostUrl()+"\nPost by "+imageListPojo.getAuthor())
                 startActivity(context, Intent.createChooser(i, "Post by "+imageListPojo.getAuthor()), null)

             }

            tvAuthorName?.text = imageListPojo.getAuthor()

            author_layout.setOnClickListener {
                /* val i = Intent(context, Main2Activity::class.java)
                 i.putExtra("url", imageListPojo.getAuthorUrl())
                 context.startActivity(i)*/

                /*val uri = Uri.parse(imageListPojo.getAuthorUrl()) // missing 'http://' will cause crashed
                val intent = Intent(Intent.ACTION_VIEW, uri)
                context.startActivity(intent)*/

                val MyDialogWeb = Dialog(context)
                MyDialogWeb.requestWindowFeature(Window.FEATURE_ACTION_BAR)
                MyDialogWeb.setContentView(R.layout.dialog_webview)
                MyDialogWeb.window.setBackgroundDrawableResource(android.R.color.transparent);
                val webviewDialog = MyDialogWeb.findViewById(R.id.webviewDialog) as WebView

                webviewDialog.getSettings().setJavaScriptEnabled(true)
                webviewDialog.getSettings().setLoadWithOverviewMode(true)
                webviewDialog.getSettings().setUseWideViewPort(true)
                webviewDialog.getSettings().setBuiltInZoomControls(true)
                webviewDialog.getSettings().setPluginState(WebSettings.PluginState.ON)
//        wb.getSettings().setPluginsEnabled(true);
                webviewDialog.setWebViewClient(HelloWebViewClient())
                webviewDialog.loadUrl(imageListPojo.getAuthorUrl())
                MyDialogWeb.show()
            }

            MyDialog.show()


        }


    }



    private fun StoragePermissionRequest() {
        if (context is MainActivity) {
            (context as MainActivity).CheckPermission()
        }
    }


    class ImageListViewHolder(itemView: View) : ViewHolder(itemView) {

        val tvAuthorName = itemView.author_name
        val tvAuthorUrl = itemView.author_url
        val author_layout = itemView.author_layout
        val mainDwnld = itemView.mainDwnld
        val mainShare = itemView.mainShare
        val Image = itemView.ImageIV

        val articleView = WebView(itemView.context)

    }

    override fun getItemCount(): Int {
        return ImageDataPojoList.size
    }


}