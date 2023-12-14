package com.example.tolaaleksey.weatherinfo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tolaaleksey.weatherinfo.classes.Day
import com.example.tolaaleksey.weatherinfo.screens.AboutScreen
import com.example.tolaaleksey.weatherinfo.screens.EditScreen
import com.example.tolaaleksey.weatherinfo.screens.HomeScreen
import com.example.tolaaleksey.weatherinfo.screens.RemoteDaysScreen
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.serialization.json.Json
import java.util.UUID


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
                    HomeScreen(navController)
                }

                composable("AboutScreen") {
                    AboutScreen(navController);
                }
                composable("RemoteDays") {
                    RemoteDaysScreen(navController);
                }

                composable("EditScreen?dayId={id}")
                { navBackStackEntry ->
                    val idString = navBackStackEntry.arguments?.getString("id")
                    val converter = GsonBuilder().create()
                    val id = converter.fromJson(idString, UUID::class.java)
                    EditScreen(navController, id = id)
                }
            }
        }

    }
}