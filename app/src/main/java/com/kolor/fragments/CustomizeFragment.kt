package com.kolor.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.RecyclerView
import com.kolor.R
import com.kolor.data.constants.Customizations
import com.kolor.data.entities.CustomizationEntity
import com.kolor.databinding.FragmentCustomizeBinding
import com.kolor.databinding.ShopitemRowBinding
import com.kolor.viewmodels.CustomizationsViewModel


class CustomizeFragment : Fragment() {

    private val customizationsViewModel by activityViewModels<CustomizationsViewModel>()
    private lateinit var binding: FragmentCustomizeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }


    override fun onResume() {
        super.onResume()
        (binding.recycleView.adapter as CategoryViewAdapter).notifyDataSetChanged()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {


        binding = FragmentCustomizeBinding.inflate(inflater, container, false).apply {
            this.vm = customizationsViewModel
            this.lifecycleOwner = viewLifecycleOwner
            this.recycleView.adapter = CategoryViewAdapter(emptyList())
        }

        customizationsViewModel.customizationItems.observe(viewLifecycleOwner){items ->
            (binding.recycleView.adapter as CategoryViewAdapter).updateItems(items)
        }

        // TODO figure something out how to observe the change only in one catorytu
        customizationsViewModel.selectedCustomizations.observe(viewLifecycleOwner){
            (binding.recycleView.adapter as CategoryViewAdapter).notifyDataSetChanged()
        }

        /*
        customizationsViewModel.gemsLiveData.observe(viewLifecycleOwner){
            (binding.recycleView.adapter as CategoryViewAdapter).notifyDataSetChanged()
        }*/

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CustomizeFragment().apply {
                arguments = Bundle().apply {

                }
            }
    }


    inner class CategoryViewAdapter( items :  List<CustomizationEntity> ) : RecyclerView.Adapter<CategoryViewAdapter.CategoryViewHolder>(){

        private var groupedItems :  List<List<CustomizationEntity>>? = groupItems(items)

        private fun groupItems(items: List<CustomizationEntity>): List<List<CustomizationEntity>>? {
            if (items.isEmpty()) {
                return null
            }
            return items.groupBy { it.type }.values.toList()
        }


        inner class CategoryViewHolder(private val view : View) : RecyclerView.ViewHolder(view){
                var categoryName : TextView = view.findViewById(R.id.categoryName)
                val shopRowRecycleView : RecyclerView = view.findViewById(R.id.categoryRecycleView)
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.shop_category, parent, false)
            return CategoryViewHolder(view)
        }


        override fun onBindViewHolder( holder: CategoryViewHolder, position: Int) {
            groupedItems?.let {items ->
                val shopRowsList = items[position]
                holder.shopRowRecycleView.adapter = ShopRowViewAdapter(shopRowsList)
                val category = when(shopRowsList[0].type){
                    Customizations.Types.CLICK_COLOR -> "Click Colors"
                    Customizations.Types.WHEEL-> "Wheel Colors"
                    Customizations.Types.BACKGROUND -> "Backgorunds"
                    else -> "Unknown Type"
                }
                holder.categoryName.text = category
            }
        }


        override fun getItemCount(): Int{
            groupedItems?.let {
                return it.size
            }
            return 0
        }


        fun updateItems(newItems :  List<CustomizationEntity>){
            groupedItems = groupItems(newItems)
            notifyDataSetChanged()
        }

    }

    inner class ShopRowViewAdapter(private var items :  List<CustomizationEntity>) : RecyclerView.Adapter<ShopRowViewAdapter.ShopRowViewHolder>() {

        inner class ShopRowViewHolder(val binding: ShopitemRowBinding) :
            RecyclerView.ViewHolder(binding.root) {}

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopRowViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            val binding = ShopitemRowBinding.inflate(inflater, parent, false).apply {
                currency = "Gems"
                textSize = 50F
                isDefault = false
            }
            return ShopRowViewHolder(binding)
        }

        override fun getItemCount(): Int = items.size


        private fun checkIfSelected(type : Int, customizationReferenceID : Int) : Boolean {


            println("valeu +== " + customizationsViewModel.selectedCustomizations.value)

            customizationsViewModel.selectedCustomizations.value?.let {selectedCustomizations ->
                return when(type) {
                    Customizations.Types.CLICK_COLOR -> {
                        println("Cheking if selected>>>> refrence id " + customizationReferenceID + " selected " + selectedCustomizations.clickColor.id)
                        return selectedCustomizations.clickColor.id == customizationReferenceID
                    }
                    Customizations.Types.WHEEL -> return selectedCustomizations.wheel.id == customizationReferenceID
                    //Customizations.Types.COLOR -> return selectedCustomizations.clickingColor == customizationReferenceID
                    else -> false
                }
            }
            return false
        }

        override fun onBindViewHolder(holder: ShopRowViewHolder, position: Int) {

            val item = items[position]


            val isBought = item.bought
            val isDefault = item.isDefault
            val price = item.price
            val name = item.name
            val type = item.type
            val id = item.id
            val referenceId = item.referenceId


            holder.binding.itemQty.text = ""
            holder.binding.itemName.text = name


            if (!isDefault) {
                holder.binding.itemPrice.amount = price.toString()
            } else {
                holder.binding.isDefault = true
            }

            // buy callback
            holder.binding.buyButton.setOnClickListener() {
                customizationsViewModel.onBuy(id, type)
            }

            if (isBought) {
                    if(checkIfSelected(type, referenceId)){
                        holder.binding.buyButton.text = "Selected"
                    }
                    else{
                        holder.binding.buyButton.text = "Select"
                    }
                } else {
                    customizationsViewModel.gemsLiveData.value?.let { gems ->
                        if (item.price > gems) {
                            holder.binding.buyButton.setBackgroundColor(
                                ContextCompat.getColor(
                                    requireContext(),
                                    R.color.unavalable_grey
                                )
                            )
                        }
                    }
                }
            }
        }
    }
