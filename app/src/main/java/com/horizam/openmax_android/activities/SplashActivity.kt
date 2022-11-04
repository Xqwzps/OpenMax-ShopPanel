package com.horizam.openmax_android.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.horizam.openmax_android.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        animateSplash()

    }

    private fun animateSplash() {
        val v = binding.ivAppLogo
        v.alpha= 0f
        v.animate().setDuration(3000).alpha(1f).withEndAction {
            overridePendingTransition(android.R.anim.accelerate_interpolator, android.R.anim.decelerate_interpolator)
            checkToken()
        }
    }

    private fun checkToken() {
        val user = Firebase.auth.currentUser
        val intent = if (user != null){
            Intent(this@SplashActivity, MainActivity::class.java)
        }else{
            Intent(this@SplashActivity, AuthActivity::class.java)
        }
        startActivity(intent)
        finish()
//        startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
//        finish()
    }
}