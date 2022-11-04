package com.horizam.openmax_android.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.horizam.openmax_android.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity() {

    lateinit var binding : ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        clickListeners()

    }

    private fun clickListeners() {
        binding.apply {
            ivBack.setOnClickListener {
                finish()
            }
        }
    }
}