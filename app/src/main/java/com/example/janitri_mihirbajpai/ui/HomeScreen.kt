package com.example.janitri_mihirbajpai.ui

import android.widget.Toast
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.example.janitri_mihirbajpai.model.ColorData
import com.example.janitri_mihirbajpai.data.ColorDatabase
import com.example.janitri_mihirbajpai.repository.ColorRepository
import com.example.janitri_mihirbajpai.viewmodel.ColorViewModel
import com.example.janitri_mihirbajpai.viewmodel.ColorViewModelFactory
import com.example.janitri_mihirbajpai.R
import com.example.janitri_mihirbajpai.ui.theme.Purple40
import com.example.janitri_mihirbajpai.ui.theme.PurpleGrey40
import com.example.janitri_mihirbajpai.ui.theme.SyncColor
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val scope = rememberCoroutineScope()
    val database = Room.databaseBuilder(
        context,
        ColorDatabase::class.java, "color_database"
    ).build()

    val repository = ColorRepository(database.colorDao())
    val colorViewModel: ColorViewModel = viewModel(factory = ColorViewModelFactory(repository))

    // Locally stored colors
    val colorsList by colorViewModel.allColors.observeAsState(emptyList())

    // Data stored in cloud
    val fetchedColors by colorViewModel.fetchedColors.observeAsState(emptyList())

    // Animate cloud store icon for 1 second
    var isAnimating by remember { mutableStateOf(false) }
    val rotation by animateFloatAsState(
        targetValue = if (isAnimating) 360f else 0f,
        animationSpec = tween(durationMillis = 1000, easing = FastOutSlowInEasing)
    )

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.color_app), color = Color.White) },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Purple40),
                actions = {
                    Box(
                        modifier = Modifier
                            .wrapContentSize()
                            .background(color = PurpleGrey40, shape = RoundedCornerShape(28.dp))
                            .padding(vertical = 8.dp, horizontal = 12.dp)
                            .clickable {
                                scope.launch {
                                    isAnimating = true
                                    colorViewModel
                                        .storeColors(colorsList)
                                        .observe(lifecycleOwner) { success ->
                                            if (success) {
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "All ${colorsList.size - fetchedColors.size} colors stored in the cloud.",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                            } else {
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Failed to store colors in the cloud.",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                            }
                                            isAnimating = false
                                        }
                                }
                            }
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(text = "${colorsList.size - fetchedColors.size}", color = Color.White, fontSize = 18.sp)
                            Spacer(modifier = Modifier.width(8.dp))
                            Icon(
                                painter = painterResource(id = R.drawable.ic_sync),
                                contentDescription = stringResource(R.string.sync_icon),
                                tint = SyncColor,
                                modifier = Modifier
                                    .size(20.dp)
                                    .graphicsLayer(rotationZ = rotation)
                            )
                        }
                    }
                }
            )
        },
        //Floating button to add new item
        floatingActionButton = {
            Box(
                modifier = Modifier
                    .wrapContentSize()
                    .background(color = PurpleGrey40, shape = RoundedCornerShape(28.dp))
                    .padding(12.dp)
                    .clickable {
                        colorViewModel.addRandomColor()
                        Toast
                            .makeText(context, "New color added successfully.", Toast.LENGTH_SHORT)
                            .show()
                    }
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = stringResource(R.string.add_color),
                        color = SyncColor,
                        fontSize = 18.sp
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Icon(
                        painter = painterResource(id = R.drawable.ic_add),
                        contentDescription = stringResource(R.string.sync_icon),
                        tint = SyncColor,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .background(Color.White)
        ) {
            ColorDataGrid(colorsList)
        }
    }
}

@Composable
fun ColorDataGrid(
    colors: List<ColorData>
) {
    LazyColumn() {
        items(colors.chunked(COLUMN_COUNT)) { rowColors ->
            Row(modifier = Modifier.fillMaxWidth()) {
                for (colorData in rowColors) {
                    ColorCard(
                        colorData = colorData,
                        modifier = Modifier.weight(1f)
                    )
                }
                if (rowColors.size < COLUMN_COUNT) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
fun ColorCard(
    colorData: ColorData,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .padding(12.dp)
            .height(100.dp)
            .width(160.dp)
            .clip(shape = RoundedCornerShape(8.dp))
            .background(color = Color(android.graphics.Color.parseColor(colorData.hex))),

        colors = CardDefaults.cardColors(
            containerColor = Color(
                android.graphics.Color.parseColor(
                    colorData.hex
                )
            )
        ),
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
        ) {
            Text(
                text = colorData.hex,
                fontSize = 18.sp,
                color = Color.White,
                modifier = Modifier
                    .align(Alignment.TopStart)
                    .padding(8.dp),
                textDecoration = TextDecoration.Underline
            )
            Column(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .wrapContentSize()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Bottom,
                horizontalAlignment = Alignment.End

            ) {
                Text(
                    text = stringResource(R.string.created_at),
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                )
                Text(
                    text = colorData.date,
                    fontSize = 14.sp,
                    color = Color.White,
                    fontWeight = FontWeight.Normal,
                )
            }
        }
    }
}

private const val COLUMN_COUNT = 2

@Preview(showBackground = true)
@Composable
private fun showUi() {
    HomeScreen()
}
