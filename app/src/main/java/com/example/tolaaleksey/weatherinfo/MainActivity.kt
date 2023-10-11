package com.example.tolaaleksey.weatherinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tolaaleksey.weatherinfo.screens.About
import com.example.tolaaleksey.weatherinfo.screens.Home


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            //val bottomSheetNavigator = rememberBottomSheetNavigator()

            NavHost(
                navController = navController,
                startDestination = "HomeScreen"
            ) {
                composable("HomeScreen") {
                    Home(navController)
                }

                composable("AboutScreen") {
                    About(navController);
                }
            }
        }

    }
}