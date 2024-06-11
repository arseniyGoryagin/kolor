package com.kolor.viewmodels;


import android.service.autofill.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope

import com.kolor.data.DbRepository;
import com.kolor.data.constants.Customizations
import com.kolor.data.entities.CustomizationEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.ref.Reference

class CustomizationsViewModel(private val repository :DbRepository) : ViewModel(){

    val customizationItems = repository.getCustomizationsLiveData()
    val gemsLiveData = repository.gems
    val selectedCustomizations = repository.selectedCustomizations




    private suspend fun checkIfCanBuy(item : CustomizationEntity) : Boolean{
        gemsLiveData.value?.let {gems ->
            return gems >= item.price.toLong()
        }
        return false
    }

    private suspend fun applyCustomization(customizationItem : CustomizationEntity, type :Int) {
        val customizationItemReferenceId = customizationItem.referenceId
        when(type){
             Customizations.Types.CLICK_COLOR -> {
                 repository.setClickingColor(customizationItemReferenceId)
             }
            Customizations.Types.WHEEL -> {
                repository.setWheel(customizationItemReferenceId)
            }
        }

    }

    fun onBuy(customizationId : Int, itemType : Int){


        viewModelScope.launch(Dispatchers.IO) {

            val customizationItems = customizationItems.value
            val customizationItem =  customizationItems?.get(customizationId-1)

            if (customizationItem != null) {

                // if is selected
                if(customizationItem.bought){
                    applyCustomization(customizationItem, itemType)
                    return@launch
                }
                if(!checkIfCanBuy(customizationItem)){
                    return@launch
                }

                // buy item
                repository.updateGems(-customizationItem.price.toLong())
                repository.setIsBought(customizationItem.id)
                applyCustomization(customizationItem, itemType)

            }
        }
    }

    companion object{
        fun factory(repository: DbRepository) : ViewModelProvider.Factory{
            return object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {

                    if(modelClass.isAssignableFrom(CustomizationsViewModel::class.java)) {
                        return CustomizationsViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Illegal argument to factory")
                }
            }
        }
    }



}