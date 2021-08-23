package com.bush_example_app.bushv.example

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.bush_example_app.bushv.example.data.*
import com.bush_example_app.bushv.example.databinding.ActivityMainBinding
import com.bush_example_app.bushv.example.presentation.showSplashScreen.SplashScreenFragment
import com.bush_example_app.bushv.example.presentation.showSplashScreen.SplashScreenFragmentDirections
import com.bush_example_app.bushv.example.presentation.weekStatistics.WeekStatistics
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        prepareBottomNavigation()
        navController =
            Navigation.findNavController(this@MainActivity, R.id.nav_host_fragment_container)
        if (savedInstanceState == null) initDatabase()
        else binding.bottomNavigation.visibility = View.VISIBLE
    }

    private fun prepareBottomNavigation() {
        binding.bottomNavigation.menu.findItem(R.id.themeChooserFragment).isChecked = true
        binding.bottomNavigation.setOnItemSelectedListener {
            val navOptions = NavOptions.Builder()
            navOptions.setLaunchSingleTop(true)
            when (it.itemId) {
                R.id.statisticsFragment -> {
                    navOptions.setPopUpTo(R.id.statisticsFragment, false)
                    navController.navigate(R.id.statisticsFragment, null, navOptions.build())
                }
                R.id.completedThemesFragment -> {
                    navOptions.setPopUpTo(R.id.completedThemesFragment, false)
                    navController.navigate(R.id.completedThemesFragment, null, navOptions.build())
                }
                R.id.themeChooserFragment -> {
                    navOptions.setPopUpTo(R.id.themeChooserFragment, false)
                    navController.navigate(R.id.themeChooserFragment, null, navOptions.build())
                }
                R.id.favoriteExamplesFragment -> {
                    navOptions.setPopUpTo(R.id.favoriteExamplesFragment, false)
                    navController.navigate(R.id.favoriteExamplesFragment, null, navOptions.build())
                }
                R.id.settingsFragment -> {
                    navOptions.setPopUpTo(R.id.settingsFragment, false)
                    navController.navigate(R.id.settingsFragment, null, navOptions.build())
                }
            }
            return@setOnItemSelectedListener true
        }
    }

    private fun initDatabase() {
        lifecycleScope.launch {
            withContext(Dispatchers.IO) {
                readPreferences()
                val database = createDatabase()
                Repository.init(database)
                Repository.instance().initCache()
                createWeekStatistics()
            }
            val hav = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)
            val splashScreen: SplashScreenFragment =
                hav?.childFragmentManager!!.fragments[0] as SplashScreenFragment
            splashScreen.closeSplashScreenCallback = { showChooserThemeFragment() }
            if (!AppPref.showSplashScreen) {
                splashScreen.close()
            } else {
                splashScreen.loadingHasCompleted()
            }
        }
    }

    private fun createWeekStatistics() {
        val sb = StringBuilder()
        Calendar.getInstance().apply {
            sb
                .append(this.get(Calendar.DAY_OF_MONTH))
                .append("/")
                .append(this.get(Calendar.MONTH))
                .append("/")
                .append(this.get(Calendar.YEAR))
        }
        val weekStatistics =
            WeekStatistics(AppPref.lastSavedLoginData, sb.toString(), AppPref.weekValues, Calendar.getInstance().get(Calendar.DAY_OF_WEEK))
        AppPref.weekStatistics = weekStatistics
    }

    private fun readPreferences() {
        AppPref.read(getPreferences(MODE_PRIVATE))
    }

    private fun showChooserThemeFragment() {
        binding.bottomNavigation.translationY = 500f
        binding.bottomNavigation.visibility = View.VISIBLE
        binding.bottomNavigation.animate()
            .translationYBy(-500f)
            .setDuration(1500)
            .withEndAction {
                if (AppPref.isFirstStart) {
                    showExplanationFragment()
                }
            }
            .start()
        val navDirection =
            SplashScreenFragmentDirections.actionSplashScreenFragmentToThemeChooserFragment()
        navController.navigate(navDirection)
    }

    private fun showExplanationFragment() {
        AppPref.isFirstStart = false
        val options = NavOptions.Builder()
        options.setEnterAnim(R.anim.explanation_fragment_enter)
        options.setExitAnim(R.anim.exit)
        options.setPopEnterAnim(R.anim.enter)
        options.setPopExitAnim(R.anim.explanation_fragment_exit)
        navController.navigate(R.id.appExplanationFragment, null, options.build())
    }

    private fun createDatabase(): AppDatabase {
        return Room.databaseBuilder(applicationContext, AppRoomDatabase::class.java, "database")
            .allowMainThreadQueries()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(db: SupportSQLiteDatabase) {
                    super.onCreate(db)
                    val filler = DatabaseFiller()
                    applicationContext.assets.list("db")?.forEach {
                        filler.createRecord("db/$it", db, applicationContext)
                    }
                    AppPref.isDatabaseExist = true
                }
            })
            .build()
    }

    override fun onStop() {
        super.onStop()
        lifecycleScope.launch(Dispatchers.IO) {
            AppPref.save(getPreferences(MODE_PRIVATE))
            Repository.instance().save()
        }
    }

    override fun onBackPressed() {
        navController.popBackStack()
        val curr = navController.currentBackStackEntry
        if (curr != null) {
            when (curr.destination.id) {
                R.id.statisticsFragment -> {
                    binding.bottomNavigation.menu.findItem(R.id.statisticsFragment).isChecked = true
                }
                R.id.completedThemesFragment, R.id.detailedCompletedTheme -> {
                    binding.bottomNavigation.menu.findItem(R.id.completedThemesFragment).isChecked = true
                }
                R.id.themeChooserFragment, R.id.appExplanationFragment, R.id.themeFragment -> {
                    binding.bottomNavigation.menu.findItem(R.id.themeChooserFragment).isChecked = true
                }
                R.id.favoriteExamplesFragment -> {
                    binding.bottomNavigation.menu.findItem(R.id.favoriteExamplesFragment).isChecked = true
                }
                R.id.settingsFragment -> {
                    binding.bottomNavigation.menu.findItem(R.id.settingsFragment).isChecked = true
                }
            }
        } else {
            finish()
        }
    }
}