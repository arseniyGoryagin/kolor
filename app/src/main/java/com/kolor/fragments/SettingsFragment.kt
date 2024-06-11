package com.kolor.fragments

import android.content.Context
import android.media.AudioManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.kolor.R
import com.kolor.databinding.FragmentSettingsBinding
import com.kolor.viewmodels.SettingsViewModel

class SettingsFragment : Fragment() {

    private val viewModel by activityViewModels<SettingsViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSettingsBinding.inflate(inflater).apply {
            this.vm  = viewModel
        }


        val audioManager = requireContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
        binding.volumeSlider.addOnChangeListener(){slider, volume, fromUser ->
                viewModel.changeVolume(audioManager, volume.toInt())
        }


        /*
        viewModel.preferences.observe(viewLifecycleOwner) {
            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            val currentVolume = audioManager.getStreamVolume(AudioManager.STREAM_MUSIC)
            val volumePercent = (currentVolume * 100) / maxVolume
            binding.volumeSlider.value = volumePercent.toFloat()
        }*/

        return binding.root
    }




    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SettingsFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}