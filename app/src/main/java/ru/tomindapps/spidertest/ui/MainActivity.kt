package ru.tomindapps.spidertest.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.arellomobile.mvp.MvpAppCompatFragment
import ru.tomindapps.spidertest.R

class MainActivity : AppCompatActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            openFragment(ListFragment())
        }
    }

    fun openFragment(fragment: MvpAppCompatFragment) {
        val ft = supportFragmentManager.beginTransaction()
        with(ft){
            replace(R.id.container, fragment)
            commit()
        }
    }
}
