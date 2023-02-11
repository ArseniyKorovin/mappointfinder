package ru.ascintegraciya.mappointfinder.presentation.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import ru.ascintegraciya.mappointfinder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initNavigation()
    }


    /**
     * Инициализировать навигацию
     */
    private fun initNavigation() {
        val navHostFragment = supportFragmentManager.findFragmentById(binding.fcvMainNavHost.id)
        navHostFragment as NavHostFragment
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(binding.fcvMainNavHost.id).navigateUp()
}