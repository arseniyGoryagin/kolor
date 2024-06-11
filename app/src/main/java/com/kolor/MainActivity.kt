package com.kolor

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.kolor.data.DbRepository
import com.kolor.data.KolorApplication
import com.kolor.services.autoClickService
import com.kolor.viewmodels.CustomizationsViewModel
import com.kolor.viewmodels.GameViewModel
import com.kolor.viewmodels.SettingsViewModel
import com.kolor.viewmodels.ShopViewModel
import com.kolor.viewmodels.TasksViewModel
import com.kolor.viewmodels.UpgradesViewModel

class MainActivity : AppCompatActivity() {


    private val SHARED_PREFS_NAME = "prefs"
    private val SHARED_PREFS_ISFIRSTLAUNCH="isFirstLaunch"

    private lateinit var gameViewModel : GameViewModel
    private lateinit var shopViewModel: ShopViewModel
    private lateinit var upgradesViewModel: UpgradesViewModel
    private lateinit var customizationsViewModel: CustomizationsViewModel
    private lateinit var tasksViewModel: TasksViewModel
    private lateinit var settingsViewModel : SettingsViewModel


    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var repository : DbRepository
    private lateinit var taskMonitor : TaskMonitor



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        repository = (application as KolorApplication).container.repository
        setContentView(R.layout.activity_main)
        setUpNavigation()
        setUpViewModels()
        setUpMedia()
        startTaskMonitor()
        setUpAppBar()
        startServices()
        if(checkIfFirstStartUp()){
            showInstructions()
            setFirstLaunch()
        }
    }

    private fun checkIfFirstStartUp() : Boolean{
        val prefs = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        return prefs.getBoolean(SHARED_PREFS_ISFIRSTLAUNCH, true)
    }


    private fun setFirstLaunch(){
        val prefs = getSharedPreferences(SHARED_PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putBoolean(SHARED_PREFS_ISFIRSTLAUNCH, false).apply()
    }

    private fun showInstructions(){
        AlertDialog.Builder(this)
            .setTitle("Instructions")
            .setMessage("Click on the screen to yearn coins (yellow) complete the tasks to earn gems (blue), by upgrades in the shop to eaern coins fatser")
            .setPositiveButton("Got it!") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    private fun setUpAppBar(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfig = AppBarConfiguration(navController.graph)
        setSupportActionBar(findViewById(R.id.topAppBar))
        setupActionBarWithNavController(navController, appBarConfig)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        return navController.navigateUp() || super.onSupportNavigateUp()
    }


    override fun onPause() {
        super.onPause()
        mediaPlayer.pause()
    }

    override fun onResume() {
        super.onResume()
        mediaPlayer.start()
    }


    private fun startTaskMonitor(){
        taskMonitor = TaskMonitor(repository, this, lifecycleScope)
        taskMonitor.startMonitor()
    }

    private fun startServices(){
        autoClickService()
        val intent = Intent(this, autoClickService::class.java)
        startService(intent)
    }

    private fun setUpMedia(){
        mediaPlayer = MediaPlayer.create(this, R.raw.background)
        mediaPlayer.isLooping = true
        mediaPlayer.start()
    }


    private fun setUpNavigation(){
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        val navController = navHostFragment.navController
        val bottomNavigation = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNavigation.setupWithNavController(navController)
    }

    private fun setUpViewModels(){
        gameViewModel = ViewModelProvider(this, GameViewModel.factory(repository))[GameViewModel::class.java]
        tasksViewModel = ViewModelProvider(this, TasksViewModel.factory(repository))[TasksViewModel::class.java]
        settingsViewModel = ViewModelProvider(this, SettingsViewModel.factory(repository))[SettingsViewModel::class.java]
        val shopFactory = ShopViewModel.factory(repository)
        shopViewModel = ViewModelProvider(this, shopFactory).get(ShopViewModel::class.java)
        val upgradesFactory = UpgradesViewModel.factory(repository)
        upgradesViewModel = ViewModelProvider(this, upgradesFactory).get(UpgradesViewModel::class.java)
        val customizationsFactory = CustomizationsViewModel.factory(repository)
        customizationsViewModel = ViewModelProvider(this, customizationsFactory).get(CustomizationsViewModel::class.java)
    }
}