package com.bloomengineering.permissionsfencedemo.ui.main

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.navigation.fragment.findNavController

import com.bloomengineering.permissionsfencedemo.R

class Sub3Fragment : Fragment() {

    companion object {
        fun newInstance() = Sub3Fragment()
    }

    private lateinit var viewModel: Sub3ViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val layoutInflater = inflater.inflate(R.layout.sub3_fragment, container, false)
        val button5 = layoutInflater.findViewById<Button>(R.id.button5)
        val navController = findNavController()
        button5.setOnClickListener {navController.navigate(R.id.action_sub3Fragment_to_mainFragment)}
        return layoutInflater
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(Sub3ViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
