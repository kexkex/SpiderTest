package ru.tomindapps.spidertest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import ru.tomindapps.spidertest.R

class MainActivity : AppCompatActivity() {

    private lateinit var container: FrameLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        container = findViewById(R.id.container)

        val defaultFragment = ListFragment()
        val ft = supportFragmentManager.beginTransaction()
        with(ft){
            replace(R.id.container, defaultFragment)
            addToBackStack(null)
            commit()
        }

    }
}
