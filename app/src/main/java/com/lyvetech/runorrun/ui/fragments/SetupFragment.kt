package com.lyvetech.runorrun.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.lyvetech.runorrun.R
import com.lyvetech.runorrun.databinding.FragmentSetupBinding
import timber.log.Timber

class SetupFragment : Fragment() {

    private lateinit var binding: FragmentSetupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSetupBinding.inflate(inflater, container, false)

        binding.fabAdd.setOnClickListener {
            findNavController().navigate(R.id.action_setupFragment_to_runFragment)
        }
        return binding.root
    }
}