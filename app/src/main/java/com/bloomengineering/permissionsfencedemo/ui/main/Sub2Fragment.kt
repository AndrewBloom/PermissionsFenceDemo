package com.bloomengineering.permissionsfencedemo.ui.main

import android.Manifest
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController
import com.bloomengineering.permissionsfencedemo.MainActivity

import com.bloomengineering.permissionsfencedemo.R

class Sub2Fragment : Fragment() {

    companion object {
        fun newInstance() = Sub2Fragment()
    }

    private lateinit var viewModel: Sub2ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutInflater = inflater.inflate(R.layout.sub2_fragment, container, false)
        val button4 = layoutInflater.findViewById<Button>(R.id.button4)
        val navController = findNavController()
        (activity as MainActivity).viewModel.permissionManager.requestEnterFence(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            null,
            null,
            null
        )
        button4.setOnClickListener {
            (activity as MainActivity).viewModel.permissionManager.exitFence(
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
            navController.navigate(R.id.action_sub2Fragment_to_mainFragment)
        }
        return layoutInflater
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Sub2ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
