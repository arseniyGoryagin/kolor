package com.kolor.viewmodels

import android.media.AudioManager
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.kolor.data.DbRepository
import com.kolor.data.entities.PreferenceEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class SettingsViewModel(private val repository: DbRepository) : ViewModel() {

    val preferences : LiveData<PreferenceEntity> = repository.getPreferencesLiveData()



    fun changeVolume(audioManager: AudioManager, value : Int) {
        val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
        val volumeLevel = (value * maxVolume) /100
        audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, volumeLevel, 0)
        viewModelScope.launch(Dispatchers.IO) {
            repository.setSoundVolume(volumeLevel)
        }
    }

    companion object {
        fun factory(repository: DbRepository) : ViewModelProvider.Factory{
            return object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if(modelClass.isAssignableFrom(SettingsViewModel::class.java)){
                        return SettingsViewModel(repository) as T

                    }
                    throw IllegalArgumentException("Unknown class")
                }
            }
        }
    }

}