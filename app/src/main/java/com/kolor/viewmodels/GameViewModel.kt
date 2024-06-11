package com.kolor.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kolor.data.DbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException


class GameViewModel(private val repository: DbRepository): ViewModel(){



    var gemsLiveData = repository.gems
    var coinsLiveData = repository.coins
    var progressLiveData = repository.progress



    // i dont  observe this
    /*
    var perClickLiveData = repository.perClick
    var autoClickLiveData = repository.autoClick
    var autoClickSpeedLiveData = repository.autoClickSpeed
    */


    var selectedCustomizations = repository.selectedCustomizations


    private var _boost = MutableLiveData<Boolean>(false)
    val isBoost : LiveData<Boolean> get() = _boost
    fun endBoost() {
        _boost.value = false
    }


    fun checkIfBoost() : Boolean {
        return progressLiveData.value?.toInt() == 99
    }


   fun setProgress(value : Long){
        viewModelScope.launch {
            repository.setProgress(value)
        }
    }


    //fix logic here!
    fun onGameWindowClick(){
        viewModelScope.launch {
            val perClickValue = repository.getPerClick()
            var finalPerClick = perClickValue
            if (_boost.value == true) {
                finalPerClick = perClickValue * 2
            }else{
            if (checkIfBoost()) {
                _boost.value = true
            } else {
                repository.updateClickProgress(1)
            }}
            repository.updateCoins(finalPerClick)
            repository.updateClickStat(1)
        }
    }


    companion object {
        fun factory(repository: DbRepository): ViewModelProvider.Factory {
            return object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if (modelClass.isAssignableFrom(GameViewModel::class.java)) {
                        return GameViewModel(repository) as T
                    }
                    throw IllegalArgumentException("Unknown class")
                }
            }
        }
    }


}

