package com.bloomengineering.permissionsfencedemo.ui.main

import android.Manifest
import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bloomengineering.permissionsfencedemo.MainActivity

import com.bloomengineering.permissionsfencedemo.R

class Sub1Fragment : Fragment() {

    companion object {
        fun newInstance() = Sub1Fragment()
    }

    private lateinit var viewModel: Sub1ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutInflater = inflater.inflate(R.layout.sub1_fragment, container, false)
        val navController = findNavController()
        val button3 = layoutInflater.findViewById<Button>(R.id.button3)
        (activity as MainActivity).viewModel.permissionManager.requestEnterFence(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            null,
            null,
            null
        )
        button3.setOnClickListener {navController.navigate(R.id.action_sub1Fragment_to_sub2Fragment)}
        return layoutInflater
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Sub1ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
