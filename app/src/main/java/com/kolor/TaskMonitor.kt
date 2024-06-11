package com.kolor

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import com.kolor.data.DbRepository
import com.kolor.data.constants.Tasks
import com.kolor.data.constants.Upgrades
import com.kolor.data.entities.StatEntity
import com.kolor.data.entities.TaskEntity
import com.kolor.data.entities.UpgradesEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskMonitor(private val repository: DbRepository, private val lifecycleOwner: LifecycleOwner, private val lifecycleScope: CoroutineScope){


    private val stats : LiveData<StatEntity> = repository.getStatLiveData()
    private val upgrades : LiveData<List<UpgradesEntity>> = repository.getUpgradesLiveData()
    private val tasks : LiveData<List<TaskEntity>> = repository.getTasksLiveData()



    private suspend fun checkIfNeedsUpdating(taskId : Int) : Boolean{
        return repository.getTaskProgress(taskId) != 100
    }

    private suspend fun updateAlotPerClick(){
        upgrades.value?.let {upgradesList ->
            val mpcUpgrade = upgradesList[Upgrades.MORE_PER_CLICK]
            val progress = (mpcUpgrade.qty * 10).toInt()
            repository.updateTaskProgress(Tasks.AlotPerClick, progress)
        }
    }

    private suspend fun updateGotItAll(){
        upgrades.value?.let {upgradesList ->
            val mpcUpgrade = upgradesList[Upgrades.MORE_PER_CLICK]
            val autoClick = upgradesList[Upgrades.AUTO_CLICK]
            val autoClickSpeed = upgradesList[Upgrades.AUTO_CLICK_SPEED]
            var progress = ((mpcUpgrade.qty + autoClick.qty + autoClickSpeed.qty) * 10).toInt()
            if(progress == 90){
                progress = 100
            }
            repository.updateTaskProgress(Tasks.GotItAll, progress)
        }
    }

    private suspend fun updateClicker(){
        stats.value?.let {stats ->
            val totalClicks = stats.clicks
            if (totalClicks < 1000) {
                val progress = (totalClicks / 10).toInt()
                repository.updateTaskProgress(Tasks.Cliker, progress)
            }
        }
    }


    fun startMonitor(){
        stats.observe(lifecycleOwner) {
            lifecycleScope.launch(Dispatchers.IO){
                if(checkIfNeedsUpdating(Tasks.Cliker)) {
                    updateClicker()
                }
            }
        }


        upgrades.observeForever(){
            lifecycleScope.launch(Dispatchers.IO){
                if(checkIfNeedsUpdating(Tasks.GotItAll)) {
                    updateGotItAll()
                }
                if(checkIfNeedsUpdating(Tasks.AlotPerClick)) {
                    updateAlotPerClick()
                }
            }
        }


    }


}