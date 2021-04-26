package com.example.authboy.ui.favorite

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.authboy.R
import com.example.authboy.ui.discover.DiscoverViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar

class FavorisFragment : Fragment() {

    lateinit var favorisViewModel: FavorisViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        favorisViewModel = ViewModelProviders.of(this).get(FavorisViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_favoris, container, false)
        val textView: TextView = root.findViewById(R.id.txt_favorite)
        favorisViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}