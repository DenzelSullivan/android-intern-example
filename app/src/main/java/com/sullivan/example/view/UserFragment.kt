package com.sullivan.example.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.findNavController
import com.sullivan.example.R
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_user, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        enterNameButton.setOnClickListener {
            val name = nameEditText.text.toString()
            nameTextView.text = "Welcome to the Food app $name"
            nameEditText.visibility = View.GONE
            enterNameButton.visibility = View.GONE
            startButton.visibility = View.VISIBLE
        }

        startButton.setOnClickListener {
            val name = nameEditText.text.toString()
            Toast.makeText(this.requireContext(), "Hello $name", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_userFragment_to_homeFragment)
        }
    }
}