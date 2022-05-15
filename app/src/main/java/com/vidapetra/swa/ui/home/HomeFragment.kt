package com.vidapetra.swa.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.viewModels
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.vidapetra.swa.R
import com.vidapetra.swa.databinding.FragmentHomeBinding
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    val layoutResId: Int = com.vidapetra.swa.R.layout.fragment_home
    lateinit var binding: FragmentHomeBinding
    val viewModel by viewModels<HomeViewModel>()

    fun onBindingCreated(binding: FragmentHomeBinding) {
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel.setData()
    }
}