package com.horizam.openmax_android.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.horizam.openmax_android.R
import com.horizam.openmax_android.activities.MainActivity
import com.horizam.openmax_android.databinding.FragmentSignInBinding
import com.horizam.openmax_android.utils.Validator


class SignInFragment : Fragment() {

    lateinit var binding : FragmentSignInBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater)

        initViews()
        clickListeners()

        return binding.root
    }

    private fun initViews() {
        auth = FirebaseAuth.getInstance()
        
    }

    private fun clickListeners() {
        binding.apply {
            cvLogin.setOnClickListener {
                validateData()
            }
            tvRegister.setOnClickListener {
                findNavController().navigate(R.id.action_signInFragment_to_signUpFragment)
            }
            tvResetPassword.setOnClickListener {
                findNavController().navigate(R.id.action_signInFragment_to_resetPasswordEmailFragment)
            }
        }
    }

    private fun validateData() {
        binding.apply {
            if (!Validator.isValidEmail(etEmail.text.toString(), true , requireContext())){
                showToast("Invalid Email")
                cvEmail.requestFocus()
            }else if(etPassword.text.toString().length < 6 ){
                showToast("Password must have at-least 6 characters")
                cvPassword.requestFocus()
            }else{
                loginUser()
            }
        }
    }

    private fun loginUser() {
        startLoading()
      binding.apply {
          auth.signInWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
              .addOnCompleteListener(requireActivity()) {
                  if (it.isSuccessful){
                      stopLoading()
                      showToast("Login Successful")
                      startActivity(Intent(requireContext(), MainActivity::class.java))
                  }else{
                      stopLoading()
                      showToast("Login Failure")
                  }
              }
      }
    }

    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message , Toast.LENGTH_SHORT).show()
    }

    private fun startLoading(){
        binding.apply {
            progressBar.visibility = View.VISIBLE
        }
    }
    private fun stopLoading(){
        binding.apply {
            progressBar.visibility = View.INVISIBLE
        }
    }

}