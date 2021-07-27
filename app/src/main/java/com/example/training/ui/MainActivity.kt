package com.example.training.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.training.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // another test comment

        // test comment 2

        navController = Navigation.findNavController(this,
            R.id.fragment
        )

        /*
        this is a commit in master
        -------------------------
         */

        // this is a test comment

        // this is a commit from newfeature branch

    }
}