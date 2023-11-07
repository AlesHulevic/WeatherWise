package com.example.tolaaleksey.weatherinfo.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.annotation.RestrictTo
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.tolaaleksey.weatherinfo.R
import com.example.tolaaleksey.weatherinfo.classes.Day
import com.example.tolaaleksey.weatherinfo.classes.HomeState
import com.example.tolaaleksey.weatherinfo.classes.HomeViewModule
import com.example.tolaaleksey.weatherinfo.classes.Weather
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import org.koin.core.scope.Scope


@Composable
fun Home(navController: NavController) {
    val viewModel = viewModel<HomeViewModule>()
    val context = LocalContext.current;
    val myState: State<HomeState> = viewModel.state.collectAsStateWithLifecycle();

    HomeScreenContent(
        onRemove = { day ->
            val job = Job()
            val scope = CoroutineScope(job)

            scope.launch {
                viewModel.onClickRemoveDay(day)
            }
        },
        onAdd = {
            navController.navigate("EditScreen?day=${null}")
            val job = Job()
            val scope = CoroutineScope(job)

            /*scope.launch {
                viewModel.onClickAddDay(day)
            }*/

            Toast.makeText(context, "You are add note", Toast.LENGTH_SHORT).show()
        },
        navController,
        myState
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreenContent(
    onRemove: (Day) -> Unit,
    onAdd: () -> Unit,
    navController: NavController,
    state: State<HomeState>
) {
    Scaffold(
        floatingActionButton = { FloatingActionButtonCompose(onAdd = onAdd) },
        topBar = { HomeTopBar(navController) },
        containerColor = Color(0xff1b1b23),
    ) { values ->
        when (state.value) {
            is HomeState.Empty -> EmptyListVariant(
                onAdd,
                Modifier
                    .fillMaxSize()
                    .padding(values)
            )

            is HomeState.DisplayingDays -> LazyColumn(modifier = Modifier.padding(values)) {
                itemsIndexed(items = (state.value as HomeState.DisplayingDays).days) { _, item ->
                    DayItem(
                        day = item,
                        onRemove = {
                            onRemove(item)
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 20.dp, vertical = 5.dp),
                        navController
                    )
                }
            }

            is HomeState.Error -> EmptyListVariant(
                onAdd,
                Modifier
                    .fillMaxSize()
                    .padding(values)
            )

            HomeState.Loading -> EmptyListVariant(
                onAdd,
                Modifier
                    .fillMaxSize()
                    .padding(values)
            )
        }

    }
}

@Composable
fun EmptyListVariant(onAdd: () -> Unit, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "There is no notes",
            color = Color.White,
            fontSize = 36.sp
        )
        Spacer(modifier = Modifier.height(20.dp))
        Button(
            onClick = { onAdd() },
            modifier = Modifier.width(100.dp)
        ) {
            Text(text = "Add")
        }
    }
}

@Composable
fun DayItem(
    day: Day,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
    navController: NavController
) {
    val context = LocalContext.current;
    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(2.dp)
        ) {
            Text(text = "Temperature: ${day.weather.temperature} С", color = Color.White)
            Text(text = "Cloudiness: ${day.weather.cloudiness}%", color = Color.White)
            Text(text = "Chance of rain: ${day.weather.chanceOfRain}%", color = Color.White)
            Text(text = "Humidity: ${day.weather.humidity}%", color = Color.White)
        }

        Column(
            modifier = Modifier
                .height(100.dp)
                .fillMaxHeight()
                .fillMaxWidth(0.5f)
                .padding(2.dp)
        ) {
            Text(text = "Description:", color = Color.White, fontWeight = FontWeight.Bold)
            Text(text = day.description, color = Color.White)
        }

        Row(modifier = Modifier.fillMaxHeight(), verticalAlignment = Alignment.CenterVertically) {
            IconButton(onClick = {
                val gson: Gson = GsonBuilder().create()
                val dayJson =gson.toJson(day)
                navController.navigate("EditScreen?day=${dayJson}")
            }) {
                Icon(
                    Icons.Default.Edit,
                    contentDescription = "Edit",
                    tint = Color.White
                )
            }

            IconButton(onClick = {
                onRemove()
                Toast.makeText(context, "You are delete note", Toast.LENGTH_SHORT).show()
            }) {
                Icon(
                    Icons.Default.Delete,
                    contentDescription = stringResource(R.string.delete),
                    tint = Color.Red
                )
            }
        }
    }
}

@Preview
@Composable
fun preview() {
    val navController = rememberNavController()
    Home(navController = navController)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(navController: NavController) {
    TopAppBar(
        navigationIcon = {
            IconButton(onClick = {
                navController.navigate("AboutScreen") {
                    popUpTo("AboutScreen")
                }
            }) {
                Icon(
                    Icons.Filled.MoreVert,
                    contentDescription = "Navigation Menu",
                    tint = Color.White
                )
            }
        },
        title = {
            Text(
                stringResource(R.string.home),
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontSize = 24.sp
            )
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(0xff1b1b23),
        )
    )
}

@Composable
fun FloatingActionButtonCompose(onAdd: () -> Unit) {
    FloatingActionButton(onClick = { onAdd() }) {
        Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add))
    }
}

