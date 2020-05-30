package com.phytal.sarona.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.phytal.sarona.R
import com.phytal.sarona.viewmodels.GradesViewModel

class GradesFragment : Fragment() {

    private lateinit var gradesViewModel: GradesViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        gradesViewModel =
                ViewModelProvider(this).get(GradesViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_grades, container, false)
        val textView: TextView = root.findViewById(R.id.text_grades)
        gradesViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
