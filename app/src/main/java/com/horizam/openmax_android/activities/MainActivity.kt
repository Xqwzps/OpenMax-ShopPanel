package com.horizam.openmax_android.activities

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.nfc.NfcAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.horizam.openmax_android.R
import com.horizam.openmax_android.databinding.ActivityMainBinding
import com.horizam.openmax_android.utils.CustomDialog

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val nfcAdapter: NfcAdapter? by lazy {
        NfcAdapter.getDefaultAdapter(this) }
    var nfcCheck : Boolean = false
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        clickListeners()


    }

    private fun initViews() {
        auth = FirebaseAuth.getInstance()
    }

    private fun clickListeners() {
        binding.apply {
            FormatTheLabel.setOnClickListener {
                startActivity(Intent(this@MainActivity, DataActivity::class.java))
            }
            SaveTheFile.setOnClickListener {
//                val dialog = CustomDialog(this@MainActivity)
//                dialog.startLoadingToWriteNfc()
                checkAdapter()

            }
            logout.setOnClickListener {
                auth.signOut()
                startActivity(Intent(this@MainActivity, AuthActivity::class.java))
                finish()
            }
        }
    }
    private fun checkAdapter() {
        if (nfcAdapter == null) {
            val builder = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
            builder.setMessage("This device doesn't support NFC.")
            builder.setPositiveButton("Cancel", null)
            val myDialog = builder.create()
            myDialog.setCanceledOnTouchOutside(true)
            myDialog.show()
        } else if (!nfcAdapter!!.isEnabled) {
            val builder = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
            builder.setTitle("NFC Disabled")
            builder.setMessage("Plesae Enable NFC")
            // txttext.setText("NFC IS NOT ENABLED. PLEASE ENABLE NFC IN SETTINGS->NFC")
            builder.setPositiveButton("Settings") { _, _ -> startActivity(Intent(Settings.ACTION_NFC_SETTINGS)) }
            builder.setNegativeButton("Cancel", null)
            val myDialog = builder.create()
            myDialog.setCanceledOnTouchOutside(true)
            myDialog.show()
        }else{
            startActivity(Intent(this@MainActivity, DataActivity::class.java))
        }
    }
}