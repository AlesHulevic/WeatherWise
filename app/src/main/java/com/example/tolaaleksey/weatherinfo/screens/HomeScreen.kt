@file:Suppress("PreviewAnnotationInFunctionWithParameters")

package com.example.tolaaleksey.weatherinfo.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tolaaleksey.weatherinfo.R
import com.example.tolaaleksey.weatherinfo.classes.Day
import com.example.tolaaleksey.weatherinfo.classes.HomeViewModule
import kotlin.reflect.KFunction1

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Home(navController: NavController) {
    val viewModel = viewModel<HomeViewModule>()
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("AboutScreen")
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
        },
        containerColor = Color(0xff1b1b23),
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            HomeScreenContent(
                items = viewModel.days,
                onRemove = viewModel::onClickRemoveDay,
                onAdd = { navController.navigate("HomeScreen") }
            )
        }
    }
}

@Composable
fun HomeScreenContent(
    items: List<Day>,
    onRemove: (Day) -> Unit,
    onAdd: () -> Unit,
) {

}

@Composable
fun DayItem(
    day: Day,
    onRemove: () -> Unit,
    modifier: Modifier = Modifier,
) {

}
