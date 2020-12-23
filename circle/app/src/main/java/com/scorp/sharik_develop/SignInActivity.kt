package com.scorp.sharik_develop

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestoreSettings
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        signOut()
        setContentView(R.layout.activity_signin)
        createSignInIntent()
    }

    private fun createSignInIntent() {
        // [START auth_fui_create_intent]
        // Choose authentication providers

        val providers = arrayListOf(
                AuthUI.IdpConfig.EmailBuilder().build(),
//                AuthUI.IdpConfig.PhoneBuilder().build(),
                AuthUI.IdpConfig.GoogleBuilder().build())
//                AuthUI.IdpConfig.FacebookBuilder().build(),
//                AuthUI.IdpConfig.TwitterBuilder().build())

        // Create and launch sign-in intent
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .build(),
                RC_SIGN_IN)
        // [END auth_fui_create_intent]
    }

    // [START auth_fui_result]
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == RC_SIGN_IN) {
            val response = IdpResponse.fromResultIntent(data)

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
    // [END auth_fui_result]

    private fun signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener {
                    // ...
                }
        // [END auth_fui_signout]
    }

    private fun delete() {
        // [START auth_fui_delete]
        AuthUI.getInstance()
                .delete(this)
                .addOnCompleteListener {
                    // ...
                }
        // [END auth_fui_delete]
    }

    private fun themeAndLogo() {
        val providers = emptyList<AuthUI.IdpConfig>()

        // [START auth_fui_theme_logo]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
//                        .setLogo(R.drawable.my_great_logo) // Set logo drawable
                        .setTheme(R.style.AppTheme_NoActionBar) // Set theme
                        .build(),
                RC_SIGN_IN)
        // [END auth_fui_theme_logo]
    }

    private fun privacyAndTerms() {
        val providers = emptyList<AuthUI.IdpConfig>()
        // [START auth_fui_pp_tos]
        startActivityForResult(
                AuthUI.getInstance()
                        .createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTosAndPrivacyPolicyUrls(
                                "https://example.com/terms.html",
                                "https://example.com/privacy.html")
                        .build(),
                RC_SIGN_IN)
        // [END auth_fui_pp_tos]
    }

    companion object {

        private const val RC_SIGN_IN = 123
    }
}