package com.kevinguitarist.healthcareappown1.database

import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.DatabaseError

data class DoctorInformation(
    val doctorName: String = "",
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
        val doctorRef = doctorsRef.child(doctorInfo.userId)

        doctorRef.setValue(doctorInfo)
            .addOnSuccessListener {
                onSuccess()
            }
            .addOnFailureListener { exception ->
                onError(exception.message ?: "Failed to save doctor information")
            }
    }

    // Function to get all doctors
    fun getAllDoctors(onSuccess: (List<DoctorInformation>) -> Unit, onError: (String) -> Unit) {
        doctorsRef.addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val doctors = mutableListOf<DoctorInformation>()
                for (doctorSnapshot in snapshot.children) {
                    val doctorInfo = doctorSnapshot.getValue(DoctorInformation::class.java)
                    doctorInfo?.let { doctors.add(it) }
                }
                onSuccess(doctors)
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error.message ?: "Failed to retrieve doctor information")
            }
        })
    }

    fun getDoctorById(doctorId: String, onSuccess: (DoctorInformation) -> Unit, onError: (String) -> Unit) {
        doctorsRef.child(doctorId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val doctorInfo = snapshot.getValue(DoctorInformation::class.java)
                if (doctorInfo != null) {
                    onSuccess(doctorInfo)
                } else {
                    onError("Doctor not found")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                onError(error.message)
            }
        })
    }
}