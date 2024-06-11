package com.kolor.fragments

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.animation.addListener
import androidx.core.animation.doOnEnd
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.room.InvalidationTracker
import com.kolor.R
import com.kolor.data.constants.Customizations
import com.kolor.databinding.FragmentGameBinding
import com.kolor.viewmodels.GameViewModel

// TODO synchronize the end at is boosted and amke sure the isBoosted GamViwe colcors upadte when the boost ends
class GameFragment : Fragment() {

    private val viewModel by activityViewModels<GameViewModel>()

    private var boostInProgress = false

    private val SHARED_PREFS_NAME = "prefs"
    private val SHARED_PREFS_ISFIRSTLAUNCH="isFirstLaunch"


    private fun checkIfFirstStartUp() : Boolean{
        val prefs = requireActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(SHARED_PREFS_ISFIRSTLAUNCH, true)
    }

    private fun setAsNotFirstLaunch(){
        val prefs = requireActivity().getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(SHARED_PREFS_ISFIRSTLAUNCH, false).apply()
    }

    private fun showInstructions(){
        val alertDialog = AlertDialog.Builder(requireContext()).setTitle("Welcome to kolor!")
            .setMessage("Press on the screen to earn points")
            .setPositiveButton("Gotn it"){dialog, _ ->
            dialog.dismiss()
        }.create()
    }

    private fun vibrate(){
        val vibrator = requireContext().getSystemService(Context.VIBRATOR_SERVICE) as Vibrator?
        if(vibrator != null && vibrator.hasVibrator())
        {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
                vibrator.vibrate(VibrationEffect.createOneShot(5, VibrationEffect.DEFAULT_AMPLITUDE))
            }else{
                @Suppress("DEPRECIATED")
                vibrator.vibrate(5)
            }
        }
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentGameBinding.inflate(inflater, container, false).apply {
                this.vm = viewModel
                this.lifecycleOwner = viewLifecycleOwner

        }

        binding.gameClickable.setOnClickListener(){
            vibrate()
            viewModel.onGameWindowClick()
        }

        viewModel.isBoost.observe(viewLifecycleOwner){isBoosted ->
            if(isBoosted && !boostInProgress){
                startBoostAnimation(binding)
                boostInProgress = true
            }
        }

        return binding.root
    }



    private fun startBoostAnimation(binding: FragmentGameBinding){
        val animator = ValueAnimator.ofInt(100, 0)
        animator.duration = 10000
        animator.addUpdateListener {
            val value = it.animatedValue as Int
            viewModel.setProgress(value.toLong())
        }

        animator.addListener(object : AnimatorListenerAdapter(){
            override fun onAnimationEnd(animation: Animator) {
                viewModel.endBoost()
                boostInProgress = false
            }
        })
        animator.start()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(checkIfFirstStartUp()){
            showInstructions()
            setAsNotFirstLaunch()
        }
    }


   /* fun startBoostAnimation(){
        val animator = ValueAnimator.ofInt(100, 0)
        animator.duration = 10000
        animator.addUpdateListener {
            val value = it.animatedValue as Int
            viewModel.setProgress(value)
        }
        animator.start()
        viewModel.setStartBoost(false)
    }*/


    /*

    */



    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            GameFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }
}