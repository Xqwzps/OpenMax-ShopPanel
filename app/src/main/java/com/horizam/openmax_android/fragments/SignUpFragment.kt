package com.horizam.openmax_android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.horizam.openmax_android.R
import com.horizam.openmax_android.databinding.FragmentSignUpBinding
import com.horizam.openmax_android.utils.Validator


class SignUpFragment : Fragment() {

    lateinit var binding : FragmentSignUpBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentSignUpBinding.inflate(layoutInflater)

        initViews()
        clickListeners()

        return binding.root

    }

    private fun initViews() {
//        FirebaseApp.initializeApp(requireContext())
        auth = FirebaseAuth.getInstance()
    }

    private fun clickListeners() {
        binding.apply {
            cvRegister.setOnClickListener {
                checkValidations()
            }
        }
    }

    private fun checkValidations() {
        binding.apply {
            if (!Validator.isValidEmail(etEmail.text.toString(), true, requireContext())){
                showToast("Invalid Email")
                cvEmail.requestFocus()
            } else if (!Validator.isValidName(etName.text.toString())){
                showToast("Invalid Name")
                cvName.requestFocus()
            } else if (etPassword.text.toString().length < 6){
                showToast("Invalid Password")
                cvPassword.requestFocus()
            } else if (!etConPassword.text.toString().equals(etPassword.text.toString())){
                showToast("Passwords does not match")
                cvConPassword.requestFocus()
            } else{
                registerUser()
            }
        }
    }

    private fun registerUser() {
        startLoading()
        binding.apply {
            auth.createUserWithEmailAndPassword(etEmail.text.toString(), etPassword.text.toString())
                .addOnCompleteListener(requireActivity()) {
                    if (it.isSuccessful){
                        stopLoading()
                        showToast("User Registration Successful")
                        findNavController().navigate(R.id.action_signUpFragment_to_signInFragment)
                    }else{
                        stopLoading()
                        showToast("Registration Failure")
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