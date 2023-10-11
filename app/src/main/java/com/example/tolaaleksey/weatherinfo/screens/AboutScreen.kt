package com.example.tolaaleksey.weatherinfo.screens

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.AbsoluteCutCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.tolaaleksey.weatherinfo.R

@Composable
fun imageButton(painter: Painter, mainText: String, subText: String) {
    Button(
        onClick = { },
        modifier = Modifier
            .fillMaxWidth()
            .height(68.dp),
        shape = AbsoluteCutCornerShape(0.dp),
        colors = ButtonDefaults.buttonColors(containerColor = Color(0xff1B1B23))
    )
    {
        Row(
            Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        )
        {
            Image(painter = painter, contentDescription = null, modifier = Modifier.size(68.dp))
            Spacer(modifier = Modifier.width(20.dp))
            Column(
                modifier = Modifier.fillMaxSize()
            )
            {
                Text(
                    text = mainText,
                    fontSize = 24.sp,
                    color = Color.White
                )
                Text(
                    text = subText,
                    fontSize = 16.sp,
                    color = Color.Gray
                )
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun About(navController: NavController) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigate("HomeScreen") {
                            popUpTo("HomeScreen") {
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
                title = {
                    Navigation
                    Text(
                        stringResource(R.string.about_lable),
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
            Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .fillMaxHeight()
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(100.dp))
            Image(
                painter = painterResource(id = R.drawable.main_icon),
                contentDescription = null,
                modifier = Modifier.height(200.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "This is an application that shows the weather in your region",
                color = Color.White,
                fontSize = 24.sp,
                modifier = Modifier.padding(horizontal = 24.dp),
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 40.dp),
                horizontalArrangement = Arrangement.SpaceBetween,

                ) {

                Image(
                    painter = painterResource(id = R.drawable.web),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clickable { }
                )

                Image(
                    painter = painterResource(id = R.drawable.star),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clickable { }
                )

                Image(
                    painter = painterResource(id = R.drawable.github),
                    contentDescription = null,
                    modifier = Modifier
                        .size(80.dp)
                        .clickable { }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            imageButton(
                painter = painterResource(id = R.drawable.help_circle),
                mainText = "Version",
                subText = "0.01"
            )

            Spacer(modifier = Modifier.height(4.dp))

            imageButton(
                painter = painterResource(id = R.drawable.telegram),
                mainText = "Telegram",
                subText = "lesha14447"
            )

            Spacer(modifier = Modifier.height(4.dp))
            imageButton(
                painter = painterResource(id = R.drawable.mail),
                mainText = "Mail",
                subText = "tola.lesha@mail.ru"
            )
        }
    }
}