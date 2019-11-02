package com.codingblocks.camerax

import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Bundle
import android.util.Log
import android.view.Surface
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.util.concurrent.Executor

class MainActivity : AppCompatActivity(), Executor {
    override fun execute(p0: Runnable) {
        p0.run()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            //Start your work for camera
            textureView.post {
                startCamera()
            }
        } else {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA), 1234
            )
        }
    }

    private fun startCamera() {
        val imageCaptureConfig = ImageCaptureConfig.Builder().apply {
            setTargetAspectRatio(AspectRatio.RATIO_16_9)
            setCaptureMode(ImageCapture.CaptureMode.MAX_QUALITY)
        }.build()

        val imageCapture = ImageCapture(imageCaptureConfig)

        captureImage.setOnClickListener {
            val file = File(externalMediaDirs.first(), "${System.currentTimeMillis()}.jpg")
            imageCapture.takePicture(file, this, object : ImageCapture.OnImageSavedListener {
                override fun onImageSaved(file: File) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        "Image Captured ${file.absolutePath}",
//                        Toast.LENGTH_LONG
//                    ).show()
                    Log.i("IMAGECAPTURE", "Image Captured ${file.absolutePath}")
                }

                override fun onError(
                    imageCaptureError: ImageCapture.ImageCaptureError,
                    message: String,
                    cause: Throwable?
                ) {
//                    Toast.makeText(
//                        this@MainActivity,
//                        "Error Capturing $message",
//                        Toast.LENGTH_LONG
//                    ).show()
                    Log.i("IMAGECAPTURE", "Error Capturing $message")

                }

            })
        }

        val previewConfig = PreviewConfig.Builder().apply {
            setTargetAspectRatio(AspectRatio.RATIO_16_9)
            setLensFacing(CameraX.LensFacing.BACK)
        }.build()

        val preview = Preview(previewConfig)

        preview.setOnPreviewOutputUpdateListener {
            val parent = textureView.parent as ViewGroup
            parent.removeView(textureView)
            parent.addView(textureView, 0)
            updateTransform();
            textureView.surfaceTexture = it.surfaceTexture
        }

        CameraX.bindToLifecycle(this, preview, imageCapture)
    }

    private fun updateTransform() {
        val matrix = Matrix()

        val centerX = textureView.width / 2f
        val centerY = textureView.height / 2f

        val rotationDegress = when (textureView.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return
        }

        matrix.postRotate(-rotationDegress.toFloat(), centerX, centerY)

        textureView.setTransform(matrix)
    }
}
