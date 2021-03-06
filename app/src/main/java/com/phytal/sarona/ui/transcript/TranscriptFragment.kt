package com.phytal.sarona.ui.transcript

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.phytal.sarona.R

class TranscriptFragment : Fragment() {

    private lateinit var transcriptViewModel: TranscriptViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        transcriptViewModel =
                ViewModelProvider(this).get(TranscriptViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_transcript, container, false)
        val textView: TextView = root.findViewById(R.id.text_transcript)
        transcriptViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}
