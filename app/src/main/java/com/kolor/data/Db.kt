package com.kolor.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.kolor.data.entities.CustomizationEntity
import com.kolor.data.daos.CustomizationsDao
import com.kolor.data.daos.SelectedCustomizationsDao
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
import com.kolor.data.entities.SelectedCustomizations
import com.kolor.data.entities.UpgradesEntity
import com.kolor.data.entities.WheelEntity


@Database(
    version = 1,
    entities = [GameEntity::class, UpgradesEntity::class, CustomizationEntity::class,
        TaskEntity::class, StatEntity::class, PreferenceEntity::class, ColorEntity::class,
         WheelEntity::class, SelectedCustomizations::class]
)
abstract class Db : RoomDatabase() {

    abstract val gameDao: GameDao
    abstract val upgradesDao : UpgradesDao
    abstract val customizationsDao : CustomizationsDao
    abstract val tasksDao : TasksDao
    abstract val statsDao : StatsDao
    abstract val preferencesDao : PreferenceDao
    abstract val SelectedCustomizationsDao : SelectedCustomizationsDao
    abstract val colorsDao : ColorsDao
    abstract val wheelDao : WheelDao

    companion object {
        @Volatile
        private var INSTANCE: Db? = null

        fun getDb(context: Context): Db {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(context.applicationContext, Db::class.java, "MY_DB").createFromAsset("database/kolor.db").build()
                Log.d("___||||||_________DatabaseState____________", "Database created: ${instance.isOpen}")
                INSTANCE = instance
                instance
            }
        }
    }
}