package com.example.authboy.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import com.example.authboy.R
import com.example.authboy.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(HomeViewModel::class.java)
        //val root = inflater.inflate(R.layout.fragment_home, container, false)
        val binding: FragmentHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false)
        //val textView: TextView = root.findViewById(R.id.text_home)
        homeViewModel.text.observe(viewLifecycleOwner, Observer {
            binding.textHome.text = it
        })
        setHasOptionsMenu(true)
        return binding.root
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.home, menu)
    }

    private fun getShareIntent(): Intent {
        val shareIntent = Intent(Intent.ACTION_SEND)
        shareIntent.setType("text/plain")
            .putExtra(Intent.EXTRA_TEXT, getString(R.string.text_share_desc))
        return shareIntent
    }

    // Method which call and start getShareIntent
    private fun shareSuccess(){
        startActivity(getShareIntent())
    }
    // method the launch the aboutFragment by the three dot menu
    private fun aboutLauncher(item: MenuItem): Boolean{
        return NavigationUI.onNavDestinationSelected(item,this.findNavController())
    }
    // the on options item selected
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.nav_share -> shareSuccess()
            R.id.nav_aboutUs -> aboutLauncher(item)
        }
        return super.onOptionsItemSelected(item)
    }
}