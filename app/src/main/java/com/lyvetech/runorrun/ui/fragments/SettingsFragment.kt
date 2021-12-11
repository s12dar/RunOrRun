package com.lyvetech.runorrun.ui.fragments

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.snackbar.Snackbar
import com.lyvetech.runorrun.databinding.FragmentSettingsBinding
import com.lyvetech.runorrun.utils.Constants.Companion.KEY_NAME
import com.lyvetech.runorrun.utils.Constants.Companion.KEY_WEIGHT
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SettingsFragment : Fragment() {

    private lateinit var binding: FragmentSettingsBinding

    @Inject
    lateinit var sharedPref: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpUserDetails()

        binding.btnEditDetails.setOnClickListener {
            btnEditPressed()
        }

        binding.btnSaveProfile.setOnClickListener {
            if (isFilledWhenBtnSavePressed()) {
                Snackbar.make(view, "Saved changes", Snackbar.LENGTH_LONG).show()
            } else {
                Snackbar.make(view, "Oops, fields can't be empty..", Snackbar.LENGTH_LONG).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun setUpUserDetails() {
        binding.tvUserName.text = sharedPref.getString(KEY_NAME, "")
        binding.tvUserWeight.text = "${sharedPref.getFloat(KEY_WEIGHT, 0f)} kg"
    }

    private fun btnEditPressed() {
        binding.clProfileDetails.visibility = View.GONE
        binding.clEditProfileDetails.visibility = View.VISIBLE
        binding.etUserName.setText(sharedPref.getString(KEY_NAME, ""))
        binding.etUserWeight.setText(sharedPref.getFloat(KEY_WEIGHT, 0f).toString())
    }

    private fun isFilledWhenBtnSavePressed(): Boolean {
        val userName = binding.etUserName.text.trim().toString()
        val userWeight = binding.etUserWeight.text.trim().toString()

        if (userName.isEmpty() || userWeight.isEmpty()) {
            return false
        }

        sharedPref.edit()
            .putString(KEY_NAME, userName)
            .putFloat(KEY_WEIGHT, userWeight.toFloat())
            .apply()

        binding.clEditProfileDetails.visibility = View.GONE
        binding.clProfileDetails.visibility = View.VISIBLE

        return true
    }
}