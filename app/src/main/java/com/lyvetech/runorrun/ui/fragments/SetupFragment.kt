package com.lyvetech.runorrun.ui.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.lyvetech.runorrun.R
import com.lyvetech.runorrun.databinding.FragmentSetupBinding
import com.lyvetech.runorrun.utils.Constants.Companion.KEY_FIRST_TIME_TOGGLE
import com.lyvetech.runorrun.utils.Constants.Companion.KEY_NAME
import com.lyvetech.runorrun.utils.Constants.Companion.KEY_WEIGHT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SetupFragment : Fragment() {

    private lateinit var binding: FragmentSetupBinding

    @Inject
    lateinit var sharedPref: SharedPreferences

    @set:Inject
    var isAppOpenedFirst = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSetupBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if (!isAppOpenedFirst) {
            val navOptions = NavOptions.Builder()
                .setPopUpTo(R.id.setupFragment, true)
                .build()
            findNavController().navigate(
                R.id.action_setupFragment_to_runFragment,
                savedInstanceState,
                navOptions
            )
        }

        binding.fabAdd.setOnClickListener {
            if (isWritePersonalPropertiesToSharedPrefSuccessful()) {
                findNavController().navigate(R.id.action_setupFragment_to_runFragment)
            }
        }
    }

    private fun isWritePersonalPropertiesToSharedPrefSuccessful(): Boolean {
        val name = binding.etUserName.text.toString()
        val weight = binding.etUserWeight.text.toString()

        if (name.isEmpty()) {
            binding.tilUserName.error = getString(R.string.err_empty_field)
            return false
        }
        if (weight.isEmpty()) {
            binding.tilUserWeight.error = getString(R.string.err_empty_field)
            return false
        }

        sharedPref.edit()
            .putString(KEY_NAME, name)
            .putFloat(KEY_WEIGHT, weight.toFloat())
            .putBoolean(KEY_FIRST_TIME_TOGGLE, false)
            .apply()
        return true
    }
}