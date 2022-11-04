package com.horizam.openmax_android.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.app.ProgressDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.horizam.openmax_android.R
import com.horizam.openmax_android.databinding.FragmentResetPasswordEmailBinding
import com.horizam.openmax_android.utils.Validator


class ResetPasswordEmailFragment : Fragment() {

    lateinit var binding : FragmentResetPasswordEmailBinding
    lateinit var auth : FirebaseAuth
    private lateinit var dialog : AlertDialog

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentResetPasswordEmailBinding.inflate(layoutInflater)

        initViews()
        clickListeners()

        return binding.root

    }

    private fun initViews() {
        auth = FirebaseAuth.getInstance()
        dialog = ProgressDialog(requireContext())
    }

    private fun clickListeners() {
        binding.apply {
            cvResetPasswordButton.setOnClickListener {
                validateFields()
            }
        }
    }

    private fun validateFields() {
        binding.apply {
            if (etEmail.text.toString().isEmpty()){
                cvEmail.requestFocus()
                etEmail.error = "Please Enter an email"
            }else if(!Validator.isValidEmail(etEmail.text.toString(), true,requireContext())){
                cvEmail.requestFocus()
                etEmail.error = "Please Enter a Valid email"
            }else{
                showDialog()
            }
        }
    }

    private fun showDialog() {
        binding.apply {
//            val dialog = ProgressDialog(requireContext())
            dialog.setTitle("Sending Forget Password Link")
            dialog.setMessage("Please wait...")
            dialog.setCanceledOnTouchOutside(false)
            dialog.show()
            resetPassword()
        }
    }

    private fun resetPassword() {
        binding.apply {
            val email = etEmail.text.toString()
            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "Link to reset your password has successfully been sent to $email",
                            Toast.LENGTH_LONG
                        )
                            .show()
                        dialog.dismiss()
                        findNavController().navigate(R.id.action_resetPasswordEmailFragment_to_signInFragment)
                    }else{
                        Toast.makeText(requireContext(),
                            "Failed sending password reset email link",
                            Toast.LENGTH_LONG)
                            .show()
                    }
                }
        }
    }
}