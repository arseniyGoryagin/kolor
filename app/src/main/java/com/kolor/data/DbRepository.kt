package com.kolor.data

import androidx.lifecycle.LiveData
import com.kolor.R
import com.kolor.data.constants.Customizations
import com.kolor.data.constants.Game
import com.kolor.data.constants.Tasks
import com.kolor.data.constants.Upgrades
import com.kolor.data.entities.CustomizationEntity
import com.kolor.data.daos.CustomizationsDao
import com.kolor.data.daos.SelectedCustomizationsDao
import com.kolor.data.entities.SelectedCustomizations
import com.kolor.data.entities.ColorEntity
import com.kolor.data.daos.ColorsDao
import com.kolor.data.daos.PreferenceDao
import com.kolor.data.entities.PreferenceEntity
import com.kolor.data.daos.GameDao
import com.kolor.data.entities.GameEntity
import com.kolor.data.entities.StatEntity
import com.kolor.data.daos.StatsDao
import com.kolor.data.entities.TaskEntity
import com.kolor.data.daos.TasksDao
import com.kolor.data.daos.UpgradesDao
import com.kolor.data.daos.WheelDao
import com.kolor.data.entities.UpgradesEntity
import com.kolor.data.entities.WheelEntity
import com.kolor.data.relations.CustomizationToColor
import com.kolor.data.relations.CustomizationToWheel


class DbRepository(db: Db) {



    private val gameDao : GameDao = db.gameDao
    private val upgradesDao : UpgradesDao = db.upgradesDao
    private val customizationsDao : CustomizationsDao = db.customizationsDao
    private val tasksDao: TasksDao = db.tasksDao
    private val statsDao : StatsDao = db.statsDao
    private val preferenceDao : PreferenceDao = db.preferencesDao
    private val selectedCustomizationsDao: SelectedCustomizationsDao = db.SelectedCustomizationsDao








    val selectedCustomizations = selectedCustomizationsDao.getSelectedCustomizationsWithCustomizations()


    fun getUpgradesLiveData(): LiveData<List<UpgradesEntity>> = upgradesDao.getUpgradesLiveData()
    fun getCustomizationsLiveData(): LiveData<List<CustomizationEntity>> = customizationsDao.getCustomizationLiveData()
    fun getTasksLiveData() : LiveData<List<TaskEntity>> = tasksDao.getTasksLiveData()
    fun getStatLiveData() : LiveData<StatEntity> = statsDao.getStatsLiveData()
    fun getPreferencesLiveData() : LiveData<PreferenceEntity> = preferenceDao.getPreferencesLiveData()
    fun getGameCustomizationsLiveData() : LiveData<SelectedCustomizations> = selectedCustomizationsDao.getGameCustomizationsLiveData()

    // get Tasks
    suspend fun getTaskProgress(taskId: Int) = tasksDao.getTaskProgress(taskId)



    // GAME -------------------------------------------------------------------------------------
    //update game
    suspend fun updatePerClick(amount: Long) = gameDao.updateColumn(Game.PERCLICK, amount)
    suspend fun updateAutoClick(amount: Long) = gameDao.updateColumn(Game.AUTOCLICK, amount)
    suspend fun updateAutoClickSpeed(amount: Long) = gameDao.updateColumn(Game.AUTOCLICKSPEED, amount)
    suspend fun updateCoins(amount  : Long) = gameDao.updateColumn(Game.COINS, amount)
    suspend fun updateGems(amount : Long) = gameDao.updateColumn(Game.GEMS, amount)
    suspend fun updateClickProgress(amount : Long) = gameDao.updateColumn(Game.PROGRESS, amount)
    //set game
    suspend fun setPerClick(amount : Long) = gameDao.setColumn(Game.PERCLICK, amount)
    suspend fun setAutoClick(amount : Long)= gameDao.setColumn(Game.AUTOCLICK, amount)
    suspend fun setAutoClickSpeed (amount : Long) = gameDao.setColumn(Game.AUTOCLICKSPEED, amount)
    suspend fun setProgress(amount : Long) = gameDao.setColumn(Game.PROGRESS, amount)
    // get game
    suspend fun getAutoClickValue(): Long  = gameDao.getColumnValue(Game.AUTOCLICK)
    suspend fun getAutoClickSpeedValue(): Long  = gameDao.getColumnValue(Game.AUTOCLICKSPEED)
    suspend fun getPerClick() = gameDao.getColumnValue(Game.PERCLICK)
    //live data
    val gems = gameDao.getColumnLiveData(Game.GEMS)
    val coins = gameDao.getColumnLiveData(Game.COINS)
    val perClick = gameDao.getColumnLiveData(Game.PERCLICK)
    val autoClick = gameDao.getColumnLiveData(Game.AUTOCLICK)
    val autoClickSpeed  = gameDao.getColumnLiveData(Game.AUTOCLICKSPEED)
    val progress = gameDao.getColumnLiveData(Game.PROGRESS)



    //update upgrades
    suspend fun updatePrice(upgradeId : Int, amount: Long) = upgradesDao.updateUpgradePrice(upgradeId, amount)
    suspend fun updateQty(upgradeId : Int,amount: Long) = upgradesDao.updateUpgradeQty(upgradeId, amount)

    // set customizations
    suspend fun setIsBought(id : Int, value : Boolean = true) = customizationsDao.updateCustomizationIsBought(id, value)

    // to cuctomizations
    suspend fun getCustomizationWithWheel(id: Int): CustomizationToWheel = customizationsDao.getWheelCustomization(id)
    suspend fun getCustomizationWithColor(id : Int) : CustomizationToColor = customizationsDao.getColorCustomization(id)

    // set customizations
    suspend fun setClickingColor(colorId : Int) = selectedCustomizationsDao.setClickingColor(colorId)
    suspend fun setWheel(wheelId : Int) = selectedCustomizationsDao.setWheel(wheelId)

    //update and set tasks
    suspend fun updateTaskProgress(taskId : Int, newProgress : Int) = tasksDao.updateProgress(taskId, newProgress)
    suspend fun setTaskProgress(taskId : Int, newProgress : Int) = tasksDao.setProgress(taskId, newProgress)
    suspend fun collectTask(taskId: Int) = tasksDao.setCollected(taskId, true)

    //update ans set stats
    suspend fun updateClickStat(value : Long) = statsDao.updateClicks(value)
    suspend fun setClickStat(value : Long) = statsDao.setClicks(value)
    suspend fun updateCoinsSpent(value : Long) = statsDao.updateCoinsSpent(value)



    //set prefernces
    suspend fun setSoundVolume(value : Int) = preferenceDao.setSoundVolume(value)



}