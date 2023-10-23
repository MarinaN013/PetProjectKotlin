package com.example.movies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase


class MainActivity : AppCompatActivity() {
    private val signInLauncher = registerForActivityResult(
        FirebaseAuthUIActivityResultContract()
    ) { res ->
        this.onSignInResult(res)//запуск самого экрана
        Log.d("testLogs", "Registration success1")
    }
    private lateinit var database: DatabaseReference//сщздан объект авторизации экрана

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val intentToAnotherScreen = Intent(this, MoviesActivity::class.java)
        startActivity(intentToAnotherScreen)
//        database = Firebase.database.reference//инициализация базы даенных
//
//        val providers = arrayListOf(
//            AuthUI.IdpConfig.EmailBuilder().build()
//        )   //список регистраций который мы используем
//
//        //Log.d("testLogs", "Registration success1")
//        val authUser = FirebaseAuth.getInstance().currentUser
//        //Log.d("testLogs", "Registration success2")
//
////        if (authUser != null) {
////            Log.d("testLogs", "Registration success21")
////            // Пользователь уже авторизован, выполните нужные действия здесь
////            val intent = Intent(this, MoviesActivity::class.java)
////            startActivity(intent)
////        }
//        // Create and launch sign-in intent
//        Log.d("testLogs", "Registration success3")
//        val signInIntent = AuthUI.getInstance()
//            .createSignInIntentBuilder()
//            .setAvailableProviders(providers)
//            //.setIsSmartLockEnabled(true)
//            .build()
////            val intent2 = Intent(this, MoviesActivity::class.java)
////            startActivity(intent2)
//        signInLauncher.launch(signInIntent)
//
  }

    private fun onSignInResult(result: FirebaseAuthUIAuthenticationResult) {
        val response = result.idpResponse
        Log.d("testLogs", "Registration success4")
        if (result.resultCode == RESULT_OK) {
            Log.d("testLogs", "Registration success${response?.email}")
            //database = Firebase.database.reference
            // Successfully signed in

            val authUser =
                FirebaseAuth.getInstance().currentUser//создаем объект текущего пользователя
            authUser?.let {//если он существует мы сохраняем его в базу данных
                val email = it.email.toString()//извлекаем емаил нашего пользователя
                val uid = it.uid// извлекаем uid нашего пользователя
                val firebaseUser = User(email, uid)// создаем новый объект с параметрами емаил и uid
                database.child("users").child(uid).setValue(firebaseUser)
                //database.setValue(firebaseUser)
                //Toast.makeText(this,"Всплывающая подсказка", Toast.LENGTH_SHORT).show()

                val intentToAnotherScreen = Intent(this, MoviesActivity::class.java)
                startActivity(intentToAnotherScreen)
            }
            // ...
        } else {
            // Sign in failed. If response is null the user canceled the
            // sign-in flow using the back button. Otherwise check
            // response.getError().getErrorCode() and handle the error.
            // ...
        }
    }
}


