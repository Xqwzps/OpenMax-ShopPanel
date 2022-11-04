package com.horizam.openmax_android.activities

import android.app.AlertDialog
import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NfcAdapter
import android.nfc.tech.NfcF
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.horizam.openmax_android.NFCUtil
import com.horizam.openmax_android.R
import com.horizam.openmax_android.databinding.ActivityDataBinding

class DataActivity : AppCompatActivity() {

    lateinit var binding: ActivityDataBinding
    lateinit var prefManager: PrefManager
    private var nfcUtil: NFCUtil = NFCUtil()
    private var intentFiltersArray: Array<IntentFilter>? = null
    private val techListsArray = arrayOf(arrayOf(NfcF::class.java.name))
    private lateinit var writeTagFilters: Array<IntentFilter>
    private val nfcAdapter: NfcAdapter? by lazy {
        NfcAdapter.getDefaultAdapter(this) }
    private var pendingIntent: PendingIntent? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initViews()
        clickListeners()
        writeNfc()
        checkAdapter()

    }

    private fun initViews() {
        prefManager = PrefManager(this)
    }


    private fun clickListeners() {
      binding.apply {
          ivBack.setOnClickListener {
              finish()
          }
          cvWriteTag.setOnClickListener {
              enableTagWriteMode()
              openBottomSheet()
          }
      }
    }

    private fun checkAdapter() {
        if (nfcAdapter == null) {
            val builder = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
            builder.setMessage("This device doesn't support NFC.")
            builder.setPositiveButton("Cancel", null)
            val myDialog = builder.create()
            myDialog.setCanceledOnTouchOutside(false)
            myDialog.show()
        }
        else if (!nfcAdapter!!.isEnabled) {
            val builder = AlertDialog.Builder(this, R.style.MyAlertDialogStyle)
            builder.setTitle("NFC Disabled")
            builder.setMessage("Plesae Enable NFC")
            // txttext.setText("NFC IS NOT ENABLED. PLEASE ENABLE NFC IN SETTINGS->NFC")
            builder.setPositiveButton("Settings") { _, _ -> startActivity(Intent(Settings.ACTION_NFC_SETTINGS)) }
            builder.setNegativeButton("Cancel", null)
            val myDialog = builder.create()
            myDialog.setCanceledOnTouchOutside(false)
            myDialog.show()
        }
    }

//    override fun onResume() {
//        super.onResume()
//        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, intentFiltersArray, techListsArray)
//        checkWriteMode()
//
//    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        if (prefManager.writeMode && NfcAdapter.ACTION_TAG_DISCOVERED == intent!!.action) {
            val productNumber = binding.etProductNumber.text.toString()
            if (!binding.etProductNumber.text.toString().isEmpty()){
                if (nfcUtil.createNFCMessage("https://openmax.pl/produkt/$productNumber", intent)) {
                    Handler(Looper.getMainLooper()).postDelayed(Runnable {
                        closeBottomSheet()
                    }, 2000)

                } else {
                    Toast.makeText(this, "Write failed", Toast.LENGTH_LONG).show()
                    closeBottomSheet()
                }
            } else {
                Toast.makeText(this, "Product Number cannot be empty", Toast.LENGTH_LONG).show()
                closeBottomSheet()
            }
        } else {
            Toast.makeText(this, "Write Mode is Disabled", Toast.LENGTH_LONG).show()
            closeBottomSheet()
        }
    }

    private fun writeNfc() {
        pendingIntent = PendingIntent.getActivity(
            this, 0, Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
            0)
        val ndef = IntentFilter(NfcAdapter.ACTION_NDEF_DISCOVERED)
        try {
            ndef.addDataType("text/plain")
        } catch (e: IntentFilter.MalformedMimeTypeException) {
            throw RuntimeException("fail", e)
        }
        intentFiltersArray = arrayOf(ndef)
    }

    private fun enableTagWriteMode() {
        prefManager.writeMode = true
//        Toast.makeText(this, "You can now write on nfc card", Toast.LENGTH_SHORT).show()
        val tagDetected = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
        writeTagFilters = arrayOf(tagDetected)
        nfcAdapter!!.enableForegroundDispatch(this, pendingIntent, writeTagFilters, null)
    }

    private fun closeBottomSheet(){
        binding.apply {
            bottomSheet.root.animate().setDuration(2000).alpha(0f).withEndAction {
                overridePendingTransition(
                    android.R.anim.fade_in,
                    android.R.anim.fade_out
                )
            }
            bottomSheet.root.visibility = View.GONE
        }
    }

    private fun openBottomSheet(){
        binding.bottomSheet.root.visibility = View.VISIBLE
    }
}