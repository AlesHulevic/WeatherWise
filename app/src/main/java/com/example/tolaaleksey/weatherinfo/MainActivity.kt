package com.example.tolaaleksey.weatherinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavType

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.tolaaleksey.weatherinfo.classes.Day
import com.example.tolaaleksey.weatherinfo.screens.About
import com.example.tolaaleksey.weatherinfo.screens.EditScreen
import com.example.tolaaleksey.weatherinfo.screens.Home
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.serialization.json.JsonBuilder


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()

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
                composable("EditScreen?day={day}")
                { navBackStackEntry ->
                    val gson: Gson = GsonBuilder().create()
                    val dayJson = navBackStackEntry.arguments?.getString("day")
                    val dayObject = gson.fromJson(dayJson, Day::class.java)
                    EditScreen(navController, day = dayObject)
                }
            }
        }

    }
}