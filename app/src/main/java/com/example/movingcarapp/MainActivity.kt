package com.example.movingcarapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        showFragment()
    }

    private fun showFragment() {
        var fragment = getFragment()
        if (fragment == null) {
            fragment = RoadFragment.newInstance()
            supportFragmentManager.beginTransaction()
                .add(R.id.activity_main_fragment_container, fragment)
                .commit()
        }
    }

    private fun getFragment(): Fragment? {
        return supportFragmentManager.findFragmentById(R.id.activity_main_fragment_container)
    }

}
