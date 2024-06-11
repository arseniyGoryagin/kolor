package com.kolor.data

import android.content.Context
import com.kolor.TaskMonitor
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

interface AppContainer {
    val repository : DbRepository
}

class DefAppContainer(context: Context) : AppContainer{
    private val db = Db.getDb(context)
    override val repository: DbRepository by lazy {
        DbRepository(db)
    }


}