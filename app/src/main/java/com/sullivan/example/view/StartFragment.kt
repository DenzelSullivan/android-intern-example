package com.sullivan.example.view

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.getSystemService
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.sullivan.example.R
import com.sullivan.example.sendNotification
import kotlinx.android.synthetic.main.fragment_start.*
import java.util.*


class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //Uncomment to set app to french
        //setAppLocale("fr")

        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        createChannel(getString(R.string.channel_id), "Study Guide Notification")

        changeTitleButton.setOnClickListener {
            val name = nameEditText.text.toString()
            enterNameTextView.text = "Hello $name"
        }

        toastButton.setOnClickListener {
            val name = nameEditText.text.toString()
            Toast.makeText(requireContext(), "Hello $name", Toast.LENGTH_SHORT).show()
        }

        snackButton.setOnClickListener {
            val name = nameEditText.text.toString()
            Snackbar.make(requireView(), "Hello $name", Snackbar.LENGTH_LONG).show()
        }

        notificationButton.setOnClickListener {
            val notificationManager = getSystemService(
                requireContext(), NotificationManager::class.java
            ) as NotificationManager

            notificationManager.sendNotification("Time to Study!!", requireContext())
        }

        nextButton.setOnClickListener {
            findNavController().navigate(R.id.action_startFragment_to_homeFragment)
        }
    }

    private fun createChannel(channelId: String, channelName: String) {
        val notificationChannel = NotificationChannel(
            channelId,
            channelName,
            NotificationManager.IMPORTANCE_HIGH
        ).apply {
            setShowBadge(false)
        }
        notificationChannel.enableLights(true)
        notificationChannel.lightColor = Color.RED
        notificationChannel.enableVibration(true)
        notificationChannel.description = "Base Notification"

        // for activity
        //val notificationManager = getSystemService(NotificationManager::class.java)

        // for fragment
        val notificationManager =
            requireActivity().getSystemService(NotificationManager::class.java)
        notificationManager?.createNotificationChannel(notificationChannel)
    }

    @Suppress("DEPRECATION")
    private fun setAppLocale(localeCode: String) {
        val config: Configuration = resources.configuration
        config.setLocale(Locale(localeCode.toLowerCase(Locale.ROOT)))
        resources.updateConfiguration(config, resources.displayMetrics)
    }
}