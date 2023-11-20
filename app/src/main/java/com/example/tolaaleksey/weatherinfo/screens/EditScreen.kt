package com.example.tolaaleksey.weatherinfo.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tolaaleksey.weatherinfo.Interfaces.HomeState
import com.example.tolaaleksey.weatherinfo.R
import com.example.tolaaleksey.weatherinfo.classes.Day
import com.example.tolaaleksey.weatherinfo.classes.EditState
import com.example.tolaaleksey.weatherinfo.classes.EditViewModule
import com.example.tolaaleksey.weatherinfo.classes.Weather
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.util.UUID

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview
@Composable
fun EditScreen(
    navController: NavController = rememberNavController(),
    id: UUID? = null
) {
    val viewModel = viewModel<EditViewModule>()

    viewModel.setStateFlow(id)
    val myState: State<EditState> = viewModel.state.collectAsStateWithLifecycle()

    EditScreenContent(
        navController,
        myState.value
    ) { day ->
        viewModel.viewModelScope.launch {
            viewModel.onClickSave(day = day)
            navController.popBackStack()
        }
    }
}

@Composable
fun EditScreenContent(
    navController: NavController,
    stateValue: EditState,
    onSave: (Day) -> Unit
) {
    Scaffold(
        topBar = { EditTopAppBar(navController) },
        containerColor = colorResource(id = R.color.night_blue),
    ) { values ->
        Column(Modifier.padding(values)) {

            when (stateValue) {
                is EditState.Loading -> {

                }

                is EditState.DisplayBook -> {
                    var newDay = Day(Weather(0, 0, 0, 0), "")
                    if (stateValue.day != null) {
                        newDay = stateValue.day
                    }
                    
                    newDay = EditFields(newDay)
                    Button(
                        onClick = {
                            onSave(newDay)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 100.dp),
                    ) { Text(text = "Save") }
                }
            }
        }
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
                    navController.popBackStack()
                }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    contentDescription = "Navigation Menu",
                    tint = Color.White
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = colorResource(id = R.color.night_blue),
        )
    )
}

@Composable
fun EditFields(day: Day): Day {
    var temerature = day.weather.temperature.toString()
    var humidity = day.weather.humidity.toString()
    var cloudiness = day.weather.cloudiness.toString()
    var chanceOfRain = day.weather.chanceOfRain.toString()
    var description = day.description

    Column(
        modifier = Modifier
            .padding(horizontal = 20.dp)
    ) {
        temerature =
            Field(temerature, "Temperature", Icons.Filled.Check, "C")
        humidity = Field(humidity, "Humidity", Icons.Filled.Check, "%")
        cloudiness =
            Field(cloudiness, "Cloudiness", Icons.Filled.Check, "%")
        chanceOfRain =
            Field(chanceOfRain, "Rain", Icons.Filled.Check, "%")
        description = Field(description, "Description", Icons.Filled.Check)
    }
    return Day(
        Weather(
            temerature.toInt(),
            humidity.toInt(),
            cloudiness.toInt(),
            chanceOfRain.toInt()
        ), description
    )
}

@Composable
fun Field(mainText: String = "", name: String, icon: ImageVector, suffix: String = ""): String {
    var text by remember { mutableStateOf(mainText) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        OutlinedTextField(
            value = text,
            onValueChange = {
                text = it
            },
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
    return text
}