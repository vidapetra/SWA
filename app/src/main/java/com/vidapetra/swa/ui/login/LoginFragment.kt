package com.vidapetra.swa.ui.login

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.vidapetra.swa.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    val layoutResId: Int = com.vidapetra.swa.R.layout.fragment_login
    lateinit var binding: FragmentLoginBinding
    val viewModel by viewModels<LoginViewModel>()

    fun onBindingCreated(binding: FragmentLoginBinding) {
        binding.vm = viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context), layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        onBindingCreated(binding)
        return binding.root
    }
}