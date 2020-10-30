package com.sullivan.example.view

import android.app.job.JobInfo
import android.app.job.JobScheduler
import android.content.ComponentName
import android.content.Context.JOB_SCHEDULER_SERVICE
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sullivan.example.R
import com.sullivan.example.notificationScheduler.NotificationJobService
import kotlinx.android.synthetic.main.fragment_notification_scheduler.*


class NotificationSchedulerFragment : Fragment() {

    private val JOB_ID = 0
    private var jobScheduler: JobScheduler? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_notification_scheduler, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (progress > 0) {
                    seekBarProgress.text = "$progress  s"
                } else {
                    seekBarProgress.text = "Not Set"
                }
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {}

            override fun onStopTrackingTouch(seekBar: SeekBar?) {}

        })

        scheduleJobButton.setOnClickListener { scheduleJob() }
        cancelJobButton.setOnClickListener { cancelJob() }

        homeButton.setOnClickListener {
            findNavController().navigate(R.id.action_notificationSchedulerFragment_to_startFragment)
        }
    }

    private fun scheduleJob() {
        jobScheduler = requireActivity().getSystemService(JOB_SCHEDULER_SERVICE) as JobScheduler

        val selectedNetworkOption = when (networkOptions.checkedRadioButtonId) {
            R.id.noNetwork -> JobInfo.NETWORK_TYPE_NONE
            R.id.anyNetwork -> JobInfo.NETWORK_TYPE_ANY
            R.id.wifiNetwork -> JobInfo.NETWORK_TYPE_UNMETERED
            else -> JobInfo.NETWORK_TYPE_NONE
        }

        val serviceName = ComponentName(
            requireActivity().packageName,
            NotificationJobService::class.java.name
        )
        val builder = JobInfo.Builder(JOB_ID, serviceName)
            .setRequiredNetworkType(selectedNetworkOption)
            .setRequiresDeviceIdle(idleSwitch.isChecked)
            .setRequiresCharging(chargingSwitch.isChecked)

        val seekBarInteger: Int = seekBar.progress
        val seekBarSet = seekBarInteger > 0

        if (seekBarSet) {
            builder.setOverrideDeadline((seekBarInteger * 1000).toLong())
        }

        val constraintSet = (selectedNetworkOption != JobInfo.NETWORK_TYPE_NONE)
                || idleSwitch.isChecked || chargingSwitch.isChecked || seekBarSet

        if (constraintSet) {
            val myJobInfo = builder.build()
            jobScheduler?.schedule(myJobInfo)

            Toast.makeText(
                requireContext(), "Job Scheduled, job will run when " +
                        "the constraints are met.", Toast.LENGTH_SHORT
            ).show()
        } else {
            Toast.makeText(
                requireContext(), "Please set at least one constraint",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun cancelJob() {
        jobScheduler?.let {
            it.cancelAll()
            jobScheduler = null
            Toast.makeText(requireContext(), "Jobs cancelled", Toast.LENGTH_SHORT).show()
        }
    }
}