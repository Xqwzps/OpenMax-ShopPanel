package com.horizam.openmax_android.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.horizam.openmax_android.R
import com.horizam.openmax_android.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    lateinit var binding : ActivityAboutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAboutBinding.inflate(layoutInflater)
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