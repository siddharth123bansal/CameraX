package com.example.camerax

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.ThumbnailUtils
import android.net.Uri
import android.os.Build
import android.os.Bundle

import android.provider.MediaStore
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import org.jetbrains.annotations.Nullable
import java.io.IOException


class HomeActivity : AppCompatActivity() {


    @RequiresApi(Build.VERSION_CODES.M)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val Btn1: Button = findViewById(R.id.btn1)
        val Btn2: Button = findViewById(R.id.btn2)
         var our_request_code: Int = 123
        var imageView: ImageView = findViewById(R.id.image_View)


        val imageSize = 32
        var our_gallery_code: Int = 1



        Btn1.setOnClickListener(View.OnClickListener {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, our_request_code)
            } else {
                requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
            }
        })

        Btn2.setOnClickListener(View.OnClickListener {
            val cameraIntent =
                Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(cameraIntent, our_gallery_code)
        })


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        var our_request_code: Int = 123
        var imageView: ImageView = findViewById(R.id.image_View)


//        val imageSize = 32
//        var our_gallery_code: Int = 1
        if(resultCode == RESULT_OK){
            if(requestCode == our_request_code){
                var image = data?.extras!!["data"] as Bitmap?
                val dimension = Math.min(image!!.width, image.height)
                image = ThumbnailUtils.extractThumbnail(image, dimension, dimension)
                imageView.setImageBitmap(image)
//                image = Bitmap.createScaledBitmap(image, imageSize, imageSize, false)
            }
            else{
                val data : Uri? = data?.data
                var image: Bitmap? = null
                try{
                    image = MediaStore.Images.Media.getBitmap(this.contentResolver, data)
                } catch (e: IOException){
                    e.printStackTrace()
                }
                imageView.setImageBitmap(image)

            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }




}