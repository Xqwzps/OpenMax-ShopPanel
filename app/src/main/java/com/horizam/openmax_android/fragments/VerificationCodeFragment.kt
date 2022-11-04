package com.horizam.openmax_android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.horizam.openmax_android.R
import com.horizam.openmax_android.databinding.FragmentVerificationCodeBinding


class VerificationCodeFragment : Fragment() {

    lateinit var binding: FragmentVerificationCodeBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentVerificationCodeBinding.inflate(layoutInflater)
        return binding.root
    }

}