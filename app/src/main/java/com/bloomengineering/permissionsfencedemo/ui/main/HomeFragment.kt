package com.bloomengineering.permissionsfencedemo.ui.main

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.bloomengineering.permissionsfencedemo.MainActivity
import com.bloomengineering.permissionsfencedemo.R

class HomeFragment : Fragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private lateinit var viewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val layoutInflater = inflater.inflate(R.layout.home_fragment, container, false)
        val navController = findNavController()
        val button = layoutInflater.findViewById<Button>(R.id.button)
        val button1 = layoutInflater.findViewById<Button>(R.id.button1)
        val button2 = layoutInflater.findViewById<Button>(R.id.button2)
        button.setOnClickListener {
            (activity as MainActivity).viewModel.permissionManager.requestEnterFence(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                { navController.navigate(R.id.action_mainFragment_to_sub1Fragment) },
                null,
                null
            )
        }
        button1.setOnClickListener {
            (activity as MainActivity).viewModel.permissionManager.requestEnterFence(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                { navController.navigate(R.id.action_mainFragment_to_sub2Fragment) },
                null,
                null
            )
        }
        button2.setOnClickListener { navController.navigate(R.id.action_mainFragment_to_sub3Fragment) }
        return layoutInflater
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(HomeViewModel::class.java)
        // TODO: Use the ViewModel
    }
}
