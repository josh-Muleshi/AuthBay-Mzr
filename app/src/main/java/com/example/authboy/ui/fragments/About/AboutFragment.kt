package com.example.authboy.ui.fragments.About

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil.inflate
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.example.authboy.R
import com.example.authboy.databinding.FragmentAboutBinding

class AboutFragment : Fragment() {

    private lateinit var aboutViewModel: AboutViewModel
    private lateinit var binding: FragmentAboutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        aboutViewModel = ViewModelProviders.of(this).get(AboutViewModel::class.java)
        binding = inflate(inflater, R.layout.fragment_about, container, false)

        aboutViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textAbout.text = it
        })
//        val fab: FloatingActionButton = root.findViewById(R.id.fab)
//        fab.setOnClickListener { view ->
//            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                .setAction("Action", null).show()
//        }
        return binding.root
    }
}
