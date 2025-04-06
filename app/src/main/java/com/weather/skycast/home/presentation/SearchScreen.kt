package com.weather.skycast.home.presentation

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.weather.skycast.home.presentation.component.HomeAction
import com.weather.skycast.home.presentation.component.HomeUiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SearchScreen(
    state: HomeUiState,
    onAction: (HomeAction) -> Unit
) {
    val context = LocalContext.current
    var debounceJob by remember { mutableStateOf<Job?>(null) }

    LaunchedEffect(state.searchText) {
        debounceJob?.cancel()
        debounceJob = launch {
            delay(800L) // Wait 500ms before firing the action
            if (state.searchText.isNotBlank()) {
                onAction(HomeAction.SubmitSearch(state.searchText))
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        OutlinedTextField(
            value = state.searchText,
            onValueChange = { onAction(HomeAction.OnSearchTextChange(it)) },
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = { Text("Search city...") },
            singleLine = true,
            trailingIcon = {
                IconButton(onClick = {
                    if (state.searchText.isNotBlank())
                        onAction(HomeAction.SubmitSearch(state.searchText))
                    else {
                        Toast.makeText(
                            context,
                            "City can not be blank",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }) {
                    Icon(
                        imageVector = Icons.Default.ArrowForward,
                        contentDescription = "Submit Search"
                    )
                }
            },
            colors = OutlinedTextFieldDefaults.colors(
                unfocusedBorderColor = Color.White.copy(alpha = 0.5f),
                focusedBorderColor = Color.White,
                cursorColor = Color.White,
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                focusedPlaceholderColor = Color.White.copy(alpha = 0.5f),
                unfocusedPlaceholderColor = Color.White.copy(alpha = 0.5f),
            ),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onAction(HomeAction.SubmitSearch(state.searchText))
                    // Hide keyboard is handled automatically by default now
                }
            )
        )

        if (state.error.isNotBlank()) {
            Text(
                text = state.error,
                color = Color.White,
                fontSize = 18.sp,
                modifier = Modifier.padding(top = 12.dp)
            )
        } else if (state.filteredCities.isEmpty() && state.searchText.isNotBlank()) {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
        LazyColumn {
            items(state.filteredCities) { city ->
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable {
                            onAction(
                                HomeAction.OnCitySelected(
                                    city.lat,
                                    city.lon
                                )
                            )
                        }
                        .padding(16.dp)
                ) {
                    Text(
                        text = "${city.name}, ${city.state}, ${city.country}",
                        color = Color.White,
                        fontSize = 18.sp
                    )
                }
                Divider(color = Color.White.copy(alpha = 0.2f))
            }
        }
    }
}