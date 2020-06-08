package ru.schedule

import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import kotlinx.android.synthetic.main.activity_main.*
import ru.schedule.day.DayFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        title = "${getString(R.string.title_schedule)} ${getString(R.string.monday)}"

        val navController = findNavController(R.id.nav_host_fragment)

        btn_monday.setOnClickListener {
            title = "${getString(R.string.title_schedule)} ${getString(R.string.monday)}"
            navController.navigate(R.id.navigation_day, Bundle().apply {
                putInt(DayFragment.DAY_NUMBER_KEY, 0)
            })
        }

        btn_tuesday.setOnClickListener {
            title = "${getString(R.string.title_schedule)} ${getString(R.string.tuesday)}"
            navController.navigate(R.id.navigation_day, Bundle().apply {
                putInt(DayFragment.DAY_NUMBER_KEY, 1)
            })
        }

        btn_wednesday.setOnClickListener {
            title = "${getString(R.string.title_schedule)} ${getString(R.string.wednesday)}"
            navController.navigate(R.id.navigation_day, Bundle().apply {
                putInt(DayFragment.DAY_NUMBER_KEY, 2)
            })
        }

        btn_thursday.setOnClickListener {
            title = "${getString(R.string.title_schedule)} ${getString(R.string.thursday)}"
            navController.navigate(R.id.navigation_day, Bundle().apply {
                putInt(DayFragment.DAY_NUMBER_KEY, 3)
            })
        }

        btn_friday.setOnClickListener {
            title = "${getString(R.string.title_schedule)} ${getString(R.string.friday)}"
            navController.navigate(R.id.navigation_day, Bundle().apply {
                putInt(DayFragment.DAY_NUMBER_KEY, 4)
            })
        }

        btn_saturday.setOnClickListener {
            title = "${getString(R.string.title_schedule)} ${getString(R.string.saturday)}"
            navController.navigate(R.id.navigation_day, Bundle().apply {
                putInt(DayFragment.DAY_NUMBER_KEY, 5)
            })
        }
    }
}
