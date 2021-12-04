package com.lyvetech.runorrun.ui.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.PolylineOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.lyvetech.runorrun.R
import com.lyvetech.runorrun.databinding.FragmentTrackingBinding
import com.lyvetech.runorrun.services.Polyline
import com.lyvetech.runorrun.services.TrackingService
import com.lyvetech.runorrun.ui.viewmodels.MainViewModel
import com.lyvetech.runorrun.utils.Constants.Companion.ACTION_PAUSE_SERVICE
import com.lyvetech.runorrun.utils.Constants.Companion.ACTION_START_OR_RESUME_SERVICE
import com.lyvetech.runorrun.utils.Constants.Companion.MAP_ZOOM
import com.lyvetech.runorrun.utils.Constants.Companion.POLYLINE_COLOR
import com.lyvetech.runorrun.utils.Constants.Companion.POLYLINE_WIDTH
import com.lyvetech.runorrun.utils.TrackingUtility
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_tracking.*

@AndroidEntryPoint
class TrackingFragment : Fragment() {

    private val viewModel: MainViewModel by viewModels()

    private var TAG = TrackingFragment::class.qualifiedName
    private lateinit var binding: FragmentTrackingBinding

    private lateinit var bottomSheetDialog: BottomSheetDialog
    private lateinit var mBtnStart: Button
    private lateinit var mBtnFinish: Button
    private lateinit var mTvTimer: TextView

    private var map: GoogleMap? = null

    private var currTimeMillis = 0L
    private var mIsTracking = false
    private var mPathPoints = mutableListOf<Polyline>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentTrackingBinding.inflate(inflater, container, false)

        // Bottom sheet layout components
        bottomSheetDialog = context?.let { BottomSheetDialog(it) }!!
        bottomSheetDialog.setContentView(R.layout.bottom_sheet_tracking)
        mBtnStart = bottomSheetDialog.findViewById(R.id.btn_start)!!
        mBtnFinish = bottomSheetDialog.findViewById(R.id.btn_finish)!!
        mTvTimer = bottomSheetDialog.findViewById(R.id.tv_timer)!!

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.mapView.onCreate(savedInstanceState)

        mapView.getMapAsync {
            map = it
            addAllPolylines()
        }

        bottomSheetDialog.behavior.state = BottomSheetBehavior.STATE_EXPANDED
        bottomSheetDialog.show()

        mBtnStart.setOnClickListener {
            startRun()
        }

        subscribeToObservers()
    }

    private fun subscribeToObservers() {
        TrackingService.isTracking.observe(viewLifecycleOwner) {
            updateTracking(it)
        }

        TrackingService.pathPoints.observe(viewLifecycleOwner) {
            mPathPoints = it
            addLatestPolyline()
            moveCameraToUser()
        }

        TrackingService.timeRunInMillis.observe(viewLifecycleOwner) {
            currTimeMillis = it
            val formattedTime = TrackingUtility.getFormattedStopWatchTime(currTimeMillis, true)
            mTvTimer.text = formattedTime
        }
    }

    private fun startRun() {
        if (mIsTracking) {
            sendCommandToTrackingService(ACTION_PAUSE_SERVICE)
        } else {
            sendCommandToTrackingService(ACTION_START_OR_RESUME_SERVICE)
        }
    }

    private fun updateTracking(isTracking: Boolean) {
        mIsTracking = isTracking
        if (!isTracking) {
            mBtnStart.text = R.string.btn_start.toString()
            mBtnFinish.visibility = View.VISIBLE
        } else {
            mBtnStart.text = R.string.btn_finish_run.toString()
            mBtnFinish.visibility = View.GONE
        }
    }

    private fun moveCameraToUser() {
        if (mPathPoints.isNotEmpty() && mPathPoints.last().isNotEmpty()) {
            map?.animateCamera(
                CameraUpdateFactory.newLatLngZoom(
                    mPathPoints.last().last(),
                    MAP_ZOOM
                )
            )
        }
    }

    private fun addAllPolylines() {
        for (polyline in mPathPoints) {
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .addAll(polyline)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun addLatestPolyline() {
        if (mPathPoints.isNotEmpty() && mPathPoints.last().size > 1) {
            val preLastLatLng =
                mPathPoints.last()[mPathPoints.last().size - 2] // second last element of last polyline in mPathPoints list
            val lastLatLng =
                mPathPoints.last().last() // last element of polyline in mPathPoints list
            val polylineOptions = PolylineOptions()
                .color(POLYLINE_COLOR)
                .width(POLYLINE_WIDTH)
                .add(preLastLatLng)
                .add(lastLatLng)
            map?.addPolyline(polylineOptions)
        }
    }

    private fun sendCommandToTrackingService(sendAction: String) =
        Intent(requireContext(), TrackingService::class.java).also {
            it.action = sendAction
            requireContext().startService(it)
        }

    override fun onResume() {
        super.onResume()
        binding.mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        binding.mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        binding.mapView.onStart()
    }

    override fun onPause() {
        super.onPause()
        binding.mapView.onStart()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        binding.mapView.onLowMemory()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.mapView.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binding.mapView.onSaveInstanceState(outState)
    }
}