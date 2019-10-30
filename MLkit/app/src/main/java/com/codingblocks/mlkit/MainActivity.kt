package com.codingblocks.mlkit

import android.content.pm.PackageManager
import android.graphics.Matrix
import android.os.Bundle
import android.os.Handler
import android.os.HandlerThread
import android.util.Rational
import android.view.Surface
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.*
import androidx.core.app.ActivityCompat
import com.google.firebase.ml.vision.FirebaseVision
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.common.FirebaseVisionImageMetadata
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private var rotation = 0
    private var lastAnalyzedTimestamp = 0L


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.CAMERA
            ) == PackageManager.PERMISSION_DENIED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.CAMERA),
                123
            )
        } else {
            textureView.post {
                startCamera()
            }
        }
    }

    private fun startCamera() {

        val analyzerConfig = ImageAnalysisConfig.Builder().apply {
            // In our analysis, we care more about the latest image than
            // analyzing *every* image
            val analyzerThread = HandlerThread(
                "LabelAnalysis"
            ).apply { start() }
            setCallbackHandler(Handler(analyzerThread.looper))
            setImageReaderMode(
                ImageAnalysis.ImageReaderMode.ACQUIRE_LATEST_IMAGE
            )
        }.build()

        val analyzerUseCase = ImageAnalysis(analyzerConfig).apply {
            analyzer = LabelAnalyzer(label)
        }

        //set Preview Config
        val previewConfig = PreviewConfig.Builder().apply {
            setTargetAspectRatio(Rational(1, 1))
            setLensFacing(CameraX.LensFacing.BACK)
        }.build()


        val preview = Preview(previewConfig)

        preview.setOnPreviewOutputUpdateListener {
            val parent = textureView.parent as ViewGroup
            parent.removeView(textureView)
            parent.addView(textureView, 0)
            updateTransform()
            textureView.surfaceTexture = it.surfaceTexture
        }

        CameraX.bindToLifecycle(this, preview, analyzerUseCase)
    }

    private fun updateTransform() {
        val matrix = Matrix()

        //compute center
        val centerX = textureView.width / 2f
        val centerY = textureView.height / 2f

        rotation = when (textureView.display.rotation) {
            Surface.ROTATION_0 -> 0
            Surface.ROTATION_90 -> 90
            Surface.ROTATION_180 -> 180
            Surface.ROTATION_270 -> 270
            else -> return

        }

        matrix.postRotate(-rotation.toFloat(), centerX, centerY)

        textureView.setTransform(matrix)
    }

    inner class LabelAnalyzer(val textView: TextView) : ImageAnalysis.Analyzer {
        override fun analyze(image: ImageProxy, rotationDegrees: Int) {
            val currentTimestamp = System.currentTimeMillis()
            if (currentTimestamp - lastAnalyzedTimestamp >=
                TimeUnit.SECONDS.toMillis(1)
            ) {
                lastAnalyzedTimestamp = currentTimestamp
                val y = image.planes[0]
                val u = image.planes[1]
                val v = image.planes[2]

                val Yb = y.buffer.remaining()
                val Ub = u.buffer.remaining()
                val Vb = v.buffer.remaining()

                val data = ByteArray(Yb + Ub + Vb)

                y.buffer.get(data, 0, Yb)
                u.buffer.get(data, Yb, Ub)
                v.buffer.get(data, Yb + Ub, Vb)

                val metadata = FirebaseVisionImageMetadata.Builder()
                    .setFormat(FirebaseVisionImageMetadata.IMAGE_FORMAT_YV12)
                    .setHeight(image.height)
                    .setWidth(image.width)
                    .setRotation(getRotation(rotationDegrees))
                    .build()

                val labelImage = FirebaseVisionImage.fromByteArray(data, metadata)

                val labeler = FirebaseVision.getInstance().getOnDeviceImageLabeler()
                labeler.processImage(labelImage)
                    .addOnSuccessListener { labels ->
                        textView.run {
                            if (labels.size >= 1) {
                                text = labels[0].text + " " + labels[0].confidence
                            }
                        }
                    }
            }

        }

        private fun getRotation(rotationCompensation: Int): Int {
            val result: Int
            when (rotationCompensation) {
                0 -> result = FirebaseVisionImageMetadata.ROTATION_0
                90 -> result = FirebaseVisionImageMetadata.ROTATION_90
                180 -> result = FirebaseVisionImageMetadata.ROTATION_180
                270 -> result = FirebaseVisionImageMetadata.ROTATION_270
                else -> {
                    result = FirebaseVisionImageMetadata.ROTATION_0
                }
            }
            return result
        }
    }
}
