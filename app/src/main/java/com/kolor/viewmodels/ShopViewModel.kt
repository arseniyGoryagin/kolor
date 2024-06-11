package com.kolor.viewmodels

import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kolor.data.DbRepository
import com.kolor.types.ShopRow
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class ShopViewModel(private val repository: DbRepository)  : ViewModel(){


        companion object {
                fun factory(repository: DbRepository) : ViewModelProvider.Factory{
                        return object : ViewModelProvider.Factory{
                                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                                        if(modelClass.isAssignableFrom(ShopViewModel::class.java)){
                                                return ShopViewModel(repository) as T

                                        }
                                        throw IllegalArgumentException("Unknown class")
                                }
                        }
                }
        }
}