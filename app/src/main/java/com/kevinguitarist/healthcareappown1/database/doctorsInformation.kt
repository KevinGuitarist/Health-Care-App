package com.kevinguitarist.healthcareappown1.database

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference

data class DoctorInformation(
    val profile: String = "",
    val experience: String = "",
    val focus: String = "",
    val about: String = "",
    val careerPath: String = "",
    val highlights: String = "",
    val workingDays: String = "",
    val workingHours: String = "",
    val imageUrl: String = "",
    val userId: String = "" // To uniquely identify each doctor
)

class DoctorDatabaseManager {
    private val database: FirebaseDatabase = FirebaseDatabase.getInstance()
    private val doctorsRef: DatabaseReference = database.getReference("doctors")

    fun saveDoctorInformation(doctorInfo: DoctorInformation, onSuccess: () -> Unit, onError: (String) -> Unit) {
        // Create a new entry using the userId as the key
        val doctorRef = doctorsRef.child(doctorInfo.userId)

        doctorRef.setValue(doctorInfo)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onError(exception.message ?: "Failed to save doctor information")
            }
    }

    fun getDoctorInformation(userId: String, onSuccess: (DoctorInformation?) -> Unit, onError: (String) -> Unit) {
        doctorsRef.child(userId).get()
            .addOnSuccessListener { snapshot ->
                val doctorInfo = snapshot.getValue(DoctorInformation::class.java)
                onSuccess(doctorInfo)
            }
            .addOnFailureListener { exception ->
                onError(exception.message ?: "Failed to retrieve doctor information")
            }
    }

}