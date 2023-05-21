package com.example.tfg4.presentation.home

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.tfg4.R


@Composable
fun principal(
     mainViewModel:MainViewModel,
     events :List<String>
){
    val searchWidgetState by mainViewModel.searchWidgetState
    val searchTextState by mainViewModel.searchTextState
    Scaffold(
        //Barra de busqueda
        topBar = {
            MainAppBar(
                searchWidgetState = searchWidgetState,
                searchTextState = searchTextState,
                onTextChange = {
                    mainViewModel.updateSearchTextState(newValue = it)
                },
                onCloseClicked = {
                    mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.CLOSED)
                },
                onSearchClicked = {
                    Log.d("Searched Text", it)
                },
                onSearchTriggered = {
                    mainViewModel.updateSearchWidgetState(newValue = SearchWidgetState.OPENED)
                }
            )
        },
        //Barra de botones de navegacion
        bottomBar = { BottomNavigation() }

    ) { padding ->
        EventList(events)
        //HomeScreen(Modifier.padding(padding))
    }
}


@Composable
fun MessageCard() {
    // Add padding around our message
    Row(modifier = Modifier.padding(all = 8.dp)) {
        Image(
           painter = painterResource(R.drawable.logo_grouping),
            contentDescription = "Contact profile picture",
            modifier = Modifier
                // Set image size to 40 dp
                .size(40.dp)
                // Clip image to be shaped as a circle
                .clip(CircleShape)
        )

        // Add a horizontal space between the image and the column
        Spacer(modifier = Modifier.width(8.dp))

        Column {
            Text(text = "Futbol Playa")

            Spacer(modifier = Modifier.height(4.dp))
            Text(text = "C/ Fernandez Ballesteros")
        }
    }
}
//Lista de eventos
@Composable
fun EventList(events: List<String>) {
    //Acceder a la base de datos para obtener la lista de eventos

    LazyColumn{
        items(events) { listaItem ->
            MessageCard()
        }
    }
}


@Composable
private fun BottomNavigation(modifier: Modifier = Modifier) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.background,
        modifier = modifier
    ) {
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.Home,
                    contentDescription = null
                )
            },
            label = {
                Text("HOME")
            },
            selected = true,
            onClick = {
                //Falta meter navegacion


            }
        )
        BottomNavigationItem(
            icon = {
                Icon(
                    imageVector = Icons.Default.AccountCircle,
                    contentDescription = null
                )
            },
            label = {
                Text("PROFILE")
            },
            selected = false,
            onClick = {
                //Falta meter navegacion




            }
        )
    }
}