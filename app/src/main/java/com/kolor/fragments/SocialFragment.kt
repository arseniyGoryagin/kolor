package com.kolor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kolor.R


class SocialFragment : Fragment() {
    private val TOTAL_CHILD_FRAGMENTS = 2


    object FragmentPositions{
        const val AUTHORIZATION_FRAGMENT = 0
        const val SCORE_FRAGMENT = 1
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_social, container, false)
        val viewPager : ViewPager2 = view.findViewById(R.id.socialViewPager)
        viewPager.adapter = SocialViewPageAdapter(requireActivity())
        val tabLayout : TabLayout = view.findViewById(R.id.socialTabLayout)
        TabLayoutMediator(tabLayout, viewPager){tab, position ->
            tab.text = when(position){
                FragmentPositions.SCORE_FRAGMENT -> "Scores"
                else -> "Profile"
            }
        }.attach()
        return view
    }



    private inner class SocialViewPageAdapter(fm : FragmentActivity) : FragmentStateAdapter(fm){

        override fun getItemCount(): Int {
            return TOTAL_CHILD_FRAGMENTS
        }

        override fun createFragment(position: Int): Fragment {
            return when(position){
                FragmentPositions.AUTHORIZATION_FRAGMENT -> AuthorizeFragment()
                FragmentPositions.SCORE_FRAGMENT -> ScoresFragment()
                else -> AuthorizeFragment()
            }
        }


    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SocialFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}