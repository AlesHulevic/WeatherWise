package com.example.tolaaleksey.weatherinfo.screens

import android.annotation.SuppressLint
import android.graphics.drawable.Icon
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.widget.Placeholder
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tolaaleksey.weatherinfo.R
import com.example.tolaaleksey.weatherinfo.classes.Day
import com.example.tolaaleksey.weatherinfo.classes.Weather
import java.time.format.TextStyle

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun EditScreen(
    navController: NavController = rememberNavController(),
    day: Day = Day(
        Weather(0, 0, 0, 0),
        stringResource(R.string.description)
    )
) {
    Scaffold(
        topBar = { EditTopAppBar(navController) },
        containerColor = Color(0xff1b1b23),
    ) {
        EditField(day)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditTopAppBar(navController: NavController) {
    TopAppBar(
        title = {
            Text(
                text = "Edit",
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 24.sp
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    navController.navigate("HomeScreen") {
                        popUpTo("HomeScreen")
                        {
                            inclusive = true
                        }
                    }
                }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Navigation Menu",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xff1b1b23),
        )
    )
}

@Composable
fun EditField(day: Day) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 20.dp)
    ) {
        Spacer(modifier = Modifier.height(60.dp))
        Field("Temperature", Icons.Filled.Check, "C")
        Field("Humidity", Icons.Filled.Check, "%")
        Field("Cloudiness", Icons.Filled.Check, "%")
        Field("Rain", Icons.Filled.Check, "%")
        Field("Description", Icons.Filled.Check)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Field(name: String, icon: ImageVector, suffix: String = "") {
    var text by remember { mutableStateOf("") }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        OutlinedTextField(
            value = text, onValueChange = { text = it },
            placeholder = {
                Text(
                    text = name,
                    color = Color.White
                )
            },
            modifier = Modifier.fillMaxWidth(),
            leadingIcon = {
                Icon(
                    icon,
                    contentDescription = null,
                    tint = Color.White
                )
            },
            suffix = {
                Text(
                    text = suffix,
                    color = Color.White
                )
            },
            textStyle = androidx.compose.ui.text.TextStyle(color = Color.White),
            )
    }
}