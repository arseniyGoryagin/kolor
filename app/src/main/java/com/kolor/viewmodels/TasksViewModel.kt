package com.kolor.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.RecyclerView
import com.kolor.data.DbRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class TasksViewModel(private val repository: DbRepository) : ViewModel() {


    val tasks = repository.getTasksLiveData()



    fun onCollect(taskId : Int){

        viewModelScope.launch(Dispatchers.IO) {

            tasks.value?.let {
                tasks.value?.let {taskList  ->
                    val task = taskList[taskId]
                    if(task.collected){
                        return@launch
                    }
                    if(task.progress == 100 || task.progress > 100){
                        repository.updateGems(task.reward)
                        repository.collectTask(taskId)
                    }
                }
            }
        }

    }

    companion object {
        fun factory(repository: DbRepository) : ViewModelProvider.Factory{
            return object : ViewModelProvider.Factory{
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    if(modelClass.isAssignableFrom(TasksViewModel::class.java)){
                        return TasksViewModel(repository) as T

                    }
                    throw IllegalArgumentException("Unknown class")
                }
            }
        }
    }








}