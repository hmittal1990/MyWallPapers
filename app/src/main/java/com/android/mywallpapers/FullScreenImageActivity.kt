package com.android.mywallpapers

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.android.utils.Constants
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_fullscreen_image.*
import android.view.MenuItem

class FullScreenImageActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fullscreen_image)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val url=intent?.extras?.getString(Constants.IMAGE)

        url?.let {
            Glide.with(this).load(url).fitCenter().into(image)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        finish()
        return true
    }
}