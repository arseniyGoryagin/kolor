package com.kolor.viewmodels;


import androidx.core.content.contentValuesOf
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.room.util.getColumnIndex

import com.kolor.data.DbRepository;
import com.kolor.data.constants.Upgrades
import com.kolor.data.entities.UpgradesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


// TODO make a singe class to inherit from for these upgrades and Customization view models

class UpgradesViewModel(private val repository :DbRepository) : ViewModel(){


    val coinsLiveData = repository.coins
    val shopItems = repository.getUpgradesLiveData()


    private suspend fun checkIfCanBuy(item : UpgradesEntity) : Boolean{
        coinsLiveData.value?.let {coins ->
            if(coins < item.price){
                return false
            }
            return true
        }
        return false
    }


    suspend fun applyUpgrade(upgradeId: Int){
        when(upgradeId){
            Upgrades.MORE_PER_CLICK -> repository.updatePerClick(1)
            Upgrades.AUTO_CLICK -> repository.updateAutoClick(1)
            Upgrades.AUTO_CLICK_SPEED -> repository.updateAutoClickSpeed(1)
        }
    }


    fun onBuy(upgradeId : Int){
        viewModelScope.launch(Dispatchers.IO) {

            val shopItems = shopItems.value

            if (shopItems != null) {
                if(!checkIfCanBuy(shopItems[upgradeId])){
                    return@launch
                }

                repository.updateCoins(-shopItems[upgradeId].price)

                applyUpgrade(upgradeId)

                println("UPdig qty")
                repository.updateQty(upgradeId, 1)
                println("UPdig price")
                repository.updatePrice(upgradeId, 2)

            }


        }
    }


    companion object{
        fun factory(repository: DbRepository) : ViewModelProvider.Factory{
                return object : ViewModelProvider.Factory{
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        if(modelClass.isAssignableFrom(UpgradesViewModel::class.java)) {
                            return UpgradesViewModel(repository) as T
                        }
                        throw IllegalArgumentException("Illigal argument to factory")
                    }
                }
        }
    }


}
