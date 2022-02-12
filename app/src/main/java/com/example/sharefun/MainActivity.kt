package com.example.sharefun

import android.app.DownloadManager
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import com.android.volley.Request
import com.android.volley.Request.*
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
import com.google.android.material.internal.ContextUtils.getActivity
import com.google.androidgamesdk.gametextinput.Listener
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var currentImageURl : String?= null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        loadmeme()



}
    fun loadmeme(){
        progressBas.visibility= View.VISIBLE
        // Instantiate the RequestQueue.

        val url = "https://meme-api.herokuapp.com/gimme"

// Request a string response from the provided URL.
        val JsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url,null,
            { response ->
                // Display the first 500 characters of the response string.
                //textView.text = "Response is: ${response.substring(0, 500)}"
                currentImageURl = response.getString("url")
                Glide.with(this).load(currentImageURl).listener(object : RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {

                        progressBas.visibility=View.GONE
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        progressBas.visibility=View.GONE
                        return false
                    }
                }).into(memeimage)

            },
            {
                Toast.makeText(this, "Something went wrong!!", Toast.LENGTH_SHORT).show()
            })

// Add the request to the RequestQueue.
        MySingleton.getInstance(this).addToRequestQueue(JsonObjectRequest)
    }
    fun shareButton(view: android.view.View) {
        val intent=Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT,"Hey, check the amazing meme I found...\n $currentImageURl")
        val chooser=Intent.createChooser(intent,"Share this meme using...")
        startActivity(chooser)

    }
    fun nextButton(view: android.view.View) {
        loadmeme()
    }
}


