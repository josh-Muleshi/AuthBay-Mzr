package com.example.authboy.ui.fragments.home

import android.annotation.SuppressLint
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.os.Message
import android.view.*
import android.widget.SeekBar
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.NavigationUI
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.authboy.R
import com.example.authboy.databinding.FragmentHomeBinding
import com.example.authboy.models.Song
import com.example.authboy.ui.SongsAdapter
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.android.synthetic.main.song_bottom_bar.*

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel
    private var totalTime: Int = 0

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
//        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            binding.textHome.text = it
//        })
        setHasOptionsMenu(true)
        return binding.root
    }


    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val songs = listOf(
            Song("came over"),
            Song("came over"),
            Song("came over"),
            Song("came over"),
            Song("came over"),
            Song("came over"),
            Song("came over")
        )

        val chate = listOf(
            R.raw.ic_chanson,
            R.raw.ic_2
        )

        val adapter = SongsAdapter(songs)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        adapter.setOnItemClickListener(object : SongsAdapter.onItemClickListener {
            override fun onItemClick(position: Int) {
                Toast.makeText(activity, "item $position clicked", Toast.LENGTH_SHORT).show()
                // new mediaPlayer Object
                val mediaPlayer = MediaPlayer.create(activity, chate[position])
                //now wanna add the seekBar's functionality
                seekbar.progress = 0
                seekbar.max = mediaPlayer.duration
                totalTime = mediaPlayer.duration
                //play button event
                playMusic(mediaPlayer)

                //add the seekBar event and our song's position

                seekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                    override fun onProgressChanged(p0: SeekBar?, position: Int, changed: Boolean) {
                        //now when will change the seekBar's position the music will go on tat position to
                        if (changed) mediaPlayer.seekTo(position)
                    }

                    override fun onStartTrackingTouch(p0: SeekBar?) {}

                    override fun onStopTrackingTouch(p0: SeekBar?) {}

                })

                Thread(Runnable {
                    while (mediaPlayer != null){
                        try {
                            var msg = Message()
                            msg.what = mediaPlayer.currentPosition
                            handler.sendMessage(msg)
                            Thread.sleep(1000)
                        }catch (e : InterruptedException){
                        }
                    }
                }).start()



                mediaPlayer.setOnCompletionListener {
                    if (position != chate.size) {
                        mediaPlayer.stop()
                        val p = position + 1
                        onItemClick(p)
                    } else {
                        play_btn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                        seekbar.progress = 0
                        mediaPlayer.stop()
                    }
                }
            }

            fun playMusic(mediaPlayer: MediaPlayer) {
                mediaPlayer.start()
                play_btn.setImageResource(R.drawable.ic_baseline_pause_24)

                play_btn.setOnClickListener {
                    // check that the mediaPlayer is not playing
                    if (mediaPlayer.isPlaying) {
                        mediaPlayer.pause()
                        play_btn.setImageResource(R.drawable.ic_baseline_play_arrow_24)
                    } else {
                        mediaPlayer.start()
                        //change the button's image
                        play_btn.setImageResource(R.drawable.ic_baseline_pause_24)
                    }

                }
            }
        })
    }

    @SuppressLint("HandlerLeak")
    var handler = object : Handler(){
        override fun handleMessage(msg: Message) {
            var currentPosition = msg.what

            //update seekBar
            seekbar.progress = currentPosition

            //update labels
            var elapsedTime = createTimeLabel(currentPosition)
            elapsedTimeLabel.text = elapsedTime

            var remainingTime = createTimeLabel(totalTime - currentPosition)
            remainingTimeLabel.text = "-$remainingTime"
        }
    }

    fun createTimeLabel(time : Int): String{
        var timeLabel = ""
        var min = time / 1000 / 60
        var sec = time / 1000 % 60

        timeLabel = "$min:"
        if (sec < 10) timeLabel += "0"
        timeLabel += sec

        return timeLabel
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
            R.id.nav_aboutUs,R.id.nav_settings -> aboutLauncher(item)
        }
        return super.onOptionsItemSelected(item)
    }
}