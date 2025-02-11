package com.kevinguitarist.healthcareappown1.database

import android.content.Context
import android.net.Uri
import com.cloudinary.android.MediaManager
import com.cloudinary.android.callback.ErrorInfo
import com.cloudinary.android.callback.UploadCallback
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException

class DoctorImageUploader {
    fun initializeCloudinary(context: Context) {
        val config = hashMapOf(
            "cloud_name" to "dpqfsltxu",
            "api_key" to "289285642872239",
            "api_secret" to "vc4dkwx2fqKPCymwae3T-7kzRcE"
        )
        MediaManager.init(context, config)
    }

    suspend fun uploadImage(context: Context, imageUri: Uri): String {
        return suspendCancellableCoroutine { continuation ->
            MediaManager.get().upload(imageUri).callback(object : UploadCallback {
                override fun onStart(requestId: String) {
                    // Upload started
                }

                override fun onProgress(requestId: String, bytes: Long, totalBytes: Long) {
                    // Upload progress
                }

                override fun onSuccess(requestId: String, resultData: Map<*, *>) {
                    val imageUrl = resultData["url"] as String
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
}