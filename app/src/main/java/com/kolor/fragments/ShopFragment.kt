package com.kolor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.kolor.R
import com.kolor.databinding.FragmentShopBinding
import com.kolor.viewmodels.CustomizationsViewModel
import com.kolor.viewmodels.GameViewModel
import com.kolor.viewmodels.ShopViewModel
import com.kolor.viewmodels.UpgradesViewModel


class ShopFragment : Fragment() {

    private val viewModel by activityViewModels<ShopViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentShopBinding.inflate(inflater, container, false).apply {
            this.vm = viewModel
        }

        binding.viewPager.adapter = PagerAdapter(requireActivity())
        //binding.viewPager.offscreenPageLimit = 2

        TabLayoutMediator(binding.tabLayout, binding.viewPager) {tab, position ->
             when(position) {
                0 -> tab.text = "Upgrades"
                1 -> tab.text = "Customization"
                else -> "?"
        }

        }.attach()

        return binding.root

    }



    private inner class PagerAdapter(fm: FragmentActivity) : FragmentStateAdapter(fm){

        override fun getItemCount(): Int {
            return 2
        }

        override fun createFragment(position: Int): Fragment {
            return  when(position){
                0 -> UpgradesFragment()
                1 -> CustomizeFragment()
                else -> throw  IllegalArgumentException("Invalid position")
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ShopFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}