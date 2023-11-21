package com.example.tolaaleksey.weatherinfo.screens

import LoadingAnimation
import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions

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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tolaaleksey.weatherinfo.R
import com.example.tolaaleksey.weatherinfo.classes.Day
import com.example.tolaaleksey.weatherinfo.classes.EditState
import com.example.tolaaleksey.weatherinfo.classes.EditViewModule
import com.example.tolaaleksey.weatherinfo.classes.Weather
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
        myState.value,
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
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        LoadingAnimation(circleColor = Color.White)
                    }
                }

                is EditState.DisplayBook -> {
                    var newDay = Day(Weather(0, 0, 0, 0), "")
                    if (stateValue.day != null) {
                        newDay = stateValue.day
                    }

                    var temperature = newDay.weather.temperature.toString()
                    var humidity = newDay.weather.humidity.toString()
                    var cloudiness = newDay.weather.cloudiness.toString()
                    var chanceOfRain = newDay.weather.chanceOfRain.toString()
                    var description = newDay.description

                    Column(
                        modifier = Modifier
                            .padding(horizontal = 20.dp)
                    ) {
                        temperature =
                            inputField(
                                temperature, "Temperature", Icons.Filled.Check, "C",
                                KeyboardOptions(keyboardType = KeyboardType.Number)
                            )
                        humidity = inputField(
                            humidity, "Humidity", Icons.Filled.Check, "%",
                            KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        cloudiness = inputField(
                            cloudiness, "Cloudiness", Icons.Filled.Check, "%",
                            KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        chanceOfRain = inputField(
                            chanceOfRain, "Rain", Icons.Filled.Check, "%",
                            KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        description = inputField(description, "Description", Icons.Filled.Check)
                    }

                    newDay.description = description

                    Button(
                        onClick = {
                            onSave(
                                Day(
                                    Weather(
                                        temperature.toInt(),
                                        humidity.toInt(),
                                        cloudiness.toInt(),
                                        chanceOfRain.toInt()
                                    ), description, id = stateValue.day?.id ?: UUID.randomUUID()
                                )
                            )
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
        ),
    )
}

@Composable
fun inputField(
    mainText: String = "",
    name: String,
    icon: ImageVector,
    suffix: String = "",
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default

): String {
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
            keyboardOptions = keyboardOptions
        )
    }
    return text
}