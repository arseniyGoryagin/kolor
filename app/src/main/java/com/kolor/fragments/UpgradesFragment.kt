package com.kolor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toDrawable
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.kolor.data.entities.UpgradesEntity
import com.kolor.databinding.FragmentUpgradesBinding
import com.kolor.databinding.ShopitemRowBinding
import com.kolor.viewmodels.UpgradesViewModel


class UpgradesFragment : Fragment() {

    private val upgradesViewModel by activityViewModels<UpgradesViewModel>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        val binding = FragmentUpgradesBinding.inflate(inflater, container, false).apply {
            this.vm = upgradesViewModel
            this.lifecycleOwner = viewLifecycleOwner
            this.recycleView.adapter  = UpgradesViewAdapter(emptyList())
        }

        upgradesViewModel.shopItems.observe(viewLifecycleOwner){items ->
            (binding.recycleView.adapter as UpgradesFragment.UpgradesViewAdapter).updateItems(items)
        }


        return binding.root
    }

    companion object {

        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UpgradesFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }



    inner class UpgradesViewAdapter(private var items : List<UpgradesEntity> ) : RecyclerView.Adapter<UpgradesViewAdapter.ShopItemViewHolder >(){

        inner class ShopItemViewHolder(val binding : ShopitemRowBinding) : RecyclerView.ViewHolder(binding.root){
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopItemViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ShopitemRowBinding.inflate(inflater, parent, false ).apply {

                currency ="Coins"
                textSize = 50F

            }
            return ShopItemViewHolder(binding)
        }

        override fun onBindViewHolder( holder: ShopItemViewHolder, position: Int) {
            val item = items[position]
            holder.binding
            holder.binding.itemName.text = item.name
            holder.binding.itemPrice.amount = item.price.toString()
            holder.binding.itemQty.text = item.qty.toString()
            holder.binding.buyButton.setOnClickListener(){
                upgradesViewModel.onBuy(item.id)
            }

        }

        override fun getItemCount(): Int {
            if(upgradesViewModel.shopItems.value != null){
                println("Items ssss ==== " + upgradesViewModel.shopItems.value!!.size)
                return upgradesViewModel.shopItems.value!!.size

            }

            return 0
        }

        fun updateItems(newItems : List<UpgradesEntity>){
            items = newItems
            notifyDataSetChanged()
        }

    }

}