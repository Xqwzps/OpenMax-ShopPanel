package com.horizam.openmax_android.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.horizam.openmax_android.R
import com.horizam.openmax_android.databinding.FragmentSetNewPasswordBinding

class SetNewPasswordFragment : Fragment() {

    lateinit var binding: FragmentSetNewPasswordBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding = FragmentSetNewPasswordBinding.inflate(layoutInflater)
        return binding.root
    }
}