package com.scorp.sharik_develop

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.firebase.ui.auth.AuthUI
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class LoadingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)

        val settings = FirebaseFirestoreSettings.Builder()
                .setCacheSizeBytes(FirebaseFirestoreSettings.CACHE_SIZE_UNLIMITED)
                .build()
        Firebase.firestore.firestoreSettings = settings

        val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())

        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setLogo(R.drawable.circle_black)
                        .setTheme(R.style.AppTheme_NoActionBar)
                        .build(),
                SIGN_IN_ACTIVITY_RESULT_SUCCESS_CODE)
    }

    public override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data == null) {
            return
        }


        if (requestCode == SIGN_IN_ACTIVITY_RESULT_SUCCESS_CODE) {
            if (resultCode == Activity.RESULT_OK) {

                val userDataBundle = Bundle()
                val mainActivityIntent = Intent(this, MainActivity::class.java)

                lifecycleScope.launch {
                    val totalScore = FirebaseHelpers.getUserData()?.balance ?: 0
                    userDataBundle.putLong("score", totalScore)
                    mainActivityIntent.putExtras(userDataBundle)
                    startActivity(mainActivityIntent)
                    finish()
                }
            }
        }
    }


    companion object {
        private const val TAG = "LoadingActivity"
        private const val SIGN_IN_ACTIVITY_RESULT_SUCCESS_CODE = 0
    }
}