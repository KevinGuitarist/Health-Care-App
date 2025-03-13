package com.kevinguitarist.healthcareappown1.database

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.core.net.toFile
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DoctorImageUploader {
    fun initializeCloudinary(context: Context) {
        val config = hashMapOf(
            "cloud_name" to "dpqfsltxu",
            "api_key" to "289285642872239",
            "api_secret" to "vc4dkwx2fqKPCymwae3T-7kzRcE",
        )
        MediaManager.init(context, config)
    }

    suspend fun uploadImage(context: Context, imageUri: Uri): String {
        return suspendCancellableCoroutine { continuation ->
            val options = HashMap<String, Any>()
            options["secure"] = true
            MediaManager.get().upload(imageUri).callback(object : UploadCallback {
                override fun onStart(requestId: String) {
                    // Upload started
                }

                override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                    // Upload progress
                }

                override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                    var imageUrl = resultData["url"] as String

                    // Check and enforce HTTPS
                    if (!imageUrl.startsWith("https://")) {
                        Log.w("Cloudinary", "Forcing HTTPS for URL: $imageUrl")
                        imageUrl = imageUrl.replace("http://", "https://")
                    }

                    if (!imageUrl.startsWith("https://")) {
                        Log.e("Cloudinary", "Image URL is still NOT HTTPS: $imageUrl")
                    }

                    continuation.resume(imageUrl)
                }


                override fun onError(requestId: String, error: ErrorInfo) {
                    continuation.resumeWithException(Exception(error.description))
                }

                override fun onReschedule(requestId: String, error: ErrorInfo) {
                    continuation.resumeWithException(Exception(error.description))
                }
            }).dispatch()
        }
    }

    private suspend fun compressImage(context: Context, imageUri: Uri): File = withContext(
        Dispatchers.IO) {
        val originalFile = imageUri.toFile()
        val bitmap = BitmapFactory.decodeFile(originalFile.absolutePath)

        val outputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream) // Adjust compression quality (50 is just an example)

        val compressedImageFile = File(context.cacheDir, "compressed_image.jpg")
        val fileOutputStream = FileOutputStream(compressedImageFile)
        fileOutputStream.write(outputStream.toByteArray())
        fileOutputStream.close()
        outputStream.close()

        return@withContext compressedImageFile
    }
}