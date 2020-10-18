package com.scorp.sharik_develop

import android.util.Log
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.tasks.await

object FirebaseHelpers {
    private const val TAG = "Firebase"

    suspend fun getUserData(): PlayerData? {
        try {
            val user = Firebase.auth.currentUser!!
            val users = Firebase.firestore.collection("users")

            val userData = users
                    .document(user.uid).get()
                    .await()

            if (userData?.data != null)
                return userData.toObject<PlayerData>()

            // No data for current user yet, creating
            val newUserData = hashMapOf("balance" to 0)
            users.document(user.uid).set(newUserData).await()

            return userData.toObject<PlayerData>()
        } catch (e: Exception) {
            Log.e(TAG, "Error getting user data", e)

            return null
        }
    }

    suspend fun setUserBalance(balance: Long) {
        try {
            val user = Firebase.auth.currentUser!!
            val users = Firebase.firestore.collection("users")
            users.document(user.uid).update("balance", balance).await()
        } catch (e: Exception) {
            Log.e(TAG, "Error setting user data", e)
        }
    }
}