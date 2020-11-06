package com.project.noticeme.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import com.google.android.gms.ads.MobileAds
import com.project.noticeme.R
import com.project.noticeme.common.ex.viewBinding
import com.project.noticeme.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val binding by viewBinding(ActivityMainBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setupNavigationController()
        MobileAds.initialize(this) {}
    }

    private fun setupNavigationController() {
        val navController = findNavController(R.id.nav_host_fragment)
        val navInflater = navController.navInflater
        val navGraph = navInflater.inflate(R.navigation.main_nav)
        navController.graph = navGraph
    }
}