package com.example.fooddelivery

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.animation.OvershootInterpolator
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.fooddelivery.data.Dish
import com.example.fooddelivery.data.DishForOrder
import com.example.fooddelivery.data.bool
import com.example.fooddelivery.data.filterStates
import com.example.fooddelivery.data.summaOrder
import com.example.fooddelivery.screens.Cart
import com.example.fooddelivery.screens.ChipGroupCompose
import com.example.fooddelivery.screens.CurrentCard
import com.example.fooddelivery.screens.CustomButton
import com.example.fooddelivery.screens.Filter
import com.example.fooddelivery.ui.theme.FoodDeliveryTheme
import com.example.fooddelivery.ui.theme.Orange
import com.example.fooddelivery.viewmodels.CartViewModel
import com.example.fooddelivery.viewmodels.DataState
import com.example.fooddelivery.viewmodels.DataStateSumma
import com.example.fooddelivery.viewmodels.MainViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val viewModel: MainViewModel by viewModels()
    private val cartviewModel: CartViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            FoodDeliveryTheme {
                Surface(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Red),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Navigation(viewModel, cartviewModel)
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    navController: NavController,
    viewModel: MainViewModel
) {
    val sheetState = rememberModalBottomSheetState()
    var showBottomSheet = remember { mutableStateOf(false) }
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = Color.White
                ),
                title = {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.logo_orange),
                                contentDescription = "logo_orange",
                                modifier = Modifier.scale(2.5f)
                            )
                        }
                    }
                },
                navigationIcon =
                {
                    Row(
                        modifier = Modifier
                            .wrapContentWidth()
                            .padding(start = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Start
                    ) {
                        Image(painter = painterResource(id = R.drawable.filter),
                            contentDescription = "Filter", modifier = Modifier
                                .scale(2.5f)
                                .clickable {
                                    showBottomSheet.value = true
                                })
                    }
                },
                actions = {
                    IconButton(onClick = {
                        navController.navigate("filter")
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.search),
                            contentDescription = "Filter", modifier = Modifier
                                .scale(2.5f)
                        )
                    }
                }
            )
        },
        bottomBar = {
            Box(
                modifier = Modifier
                    .shadow(1.dp)
                    .fillMaxWidth()
            ) {
                viewModel.fetchDataSummaCart()
                SetDataSumma(viewModel)
                if (summaOrder != 0)
                    CustomButton(
                        clickAction = { navController.navigate("cart") },
                        textButton = "$summaOrder ₽",
                        icon = R.drawable.cart,
                        colorConteiner = Orange,
                        colorFont = Color.White
                    )
                else
                    CustomButton(
                        clickAction = { navController.navigate("cart") },
                        textButton = "Добавьте что-нибудь в корзину:)",
                        icon = R.drawable.cart,
                        colorConteiner = Orange,
                        colorFont = Color.White
                    )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                var selected = remember { mutableStateOf("") }
                selected.value = ChipGroupCompose()
                when (selected.value) {
                    "Роллы" -> loadCart(showBottomSheet, sheetState, viewModel, selected)
                    "Суши" -> loadCart(showBottomSheet, sheetState, viewModel, selected)
                    "Супы" -> loadCart(showBottomSheet, sheetState, viewModel, selected)
                    "Наборы" -> loadCart(showBottomSheet, sheetState, viewModel, selected)
                    "Горячие блюда" -> loadCart(showBottomSheet, sheetState, viewModel, selected)
                    "Десерты" -> loadCart(showBottomSheet, sheetState, viewModel, selected)
                }
            }
            SetData(viewModel = viewModel)
        }
    }
}

@Composable
@OptIn(ExperimentalMaterial3Api::class)
fun loadCart(
    showBottomSheet: MutableState<Boolean>,
    sheetState: SheetState,
    viewModel: MainViewModel,
    selected: MutableState<String>
) {
    var list = mutableListOf("", "", "")
    val coroutineScope = rememberCoroutineScope()
    if (showBottomSheet.value) {
        ModalBottomSheet(
            onDismissRequest = {
                showBottomSheet.value = false
            },
            sheetState = sheetState
        ) {
            list = BottomSheetFilter()
            CustomButton(clickAction = {
                coroutineScope.launch {
                    bool = true
                    sheetState.hide()
                }.invokeOnCompletion {
                    if (!sheetState.isVisible) {
                        showBottomSheet.value = false
                    }
                }
            }, textButton = "Готово", icon = 0, colorConteiner = Orange, colorFont = Color.White)
        }

    }
    if (!filterStates[0] && !filterStates[1] && !filterStates[2]) {
        bool = false
    }

    if (bool == false)
        viewModel.fetchDatabyCategory(selected.value)
    else {
        if (filterStates[0])
            list[0] = "vegetarian"
        if (filterStates[1])
            list[1] = "hot"
        if (filterStates[2])
            list[2] = "sale"

        viewModel.fetchDatabyCategoryAndFilter(
            selected.value,
            list
        )
    }
}

@Composable
fun SetDataSumma(viewModel: MainViewModel) {
    when (val result = viewModel.responseSumma.value) {
        is DataStateSumma.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is DataStateSumma.Success -> {
            ShowSumma(result.data)
        }

        is DataStateSumma.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = result.message,
                )
            }
        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error Fetching data",
                )
            }
        }
    }
}

@Composable
fun ShowSumma(data: MutableList<DishForOrder>) {
    var summa = 0
    for (i in 0 until data.size) {
        summa += data[i].currentPrice!!
    }
    summaOrder = summa
}

@Composable
fun SetData(viewModel: MainViewModel) {
    Log.d("MyLog", "viewModel.response.value ${viewModel.response.value}")
    when (val result = viewModel.response.value) {
        is DataState.Loading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is DataState.Success -> {
            ShowLazyList(result.data)
        }

        is DataState.Failure -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = result.message,
                )
            }
        }

        else -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Error Fetching data",
                )
            }
        }
    }
}

@Composable
fun ShowLazyList(data: MutableList<Dish>) {
    if (data.isEmpty()) {
        Box(
            modifier = Modifier
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Таких блюд нет :(\n" +
                        "Попробуйте изменить фильтры",
                fontSize = 16.sp,
                fontWeight = FontWeight.W400,
                color = Color.Gray,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .wrapContentHeight(Alignment.CenterVertically)
            )
        }
    } else {
        val showCurrentFood = remember { mutableStateOf(false) }
        LazyVerticalGrid(
            columns = GridCells.Adaptive(minSize = 170.dp),
            modifier = Modifier
        ) {
            items(data) { food ->
                CurrentCard(showCurrentFood = showCurrentFood, dish = food)

            }
        }
    }
}

@Composable
@Preview
fun BottomSheetFilter(): MutableList<String> {
    var result: MutableList<String> = mutableListOf("non", "non", "non")
    result[0] = "dkdk"
    Text(
        "Подобрать блюда",
        fontSize = 20.sp,
        fontWeight = FontWeight.Bold,
        modifier = Modifier.padding(start = 24.dp)
    )
    Column(modifier = Modifier.padding(start = 24.dp, top = 16.dp, bottom = 16.dp, end = 40.dp)) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            val checkedState = remember { mutableStateOf(filterStates[0]) }

            Text(
                "Без мяса", modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
            )
            Checkbox(
                checked = checkedState.value,
                colors = CheckboxDefaults.colors(
                    checkedColor = Orange,
                    uncheckedColor = Color.LightGray
                ),
                onCheckedChange = {
                    checkedState.value = it
                    filterStates[0] = it
                    if (it)
                        result[0] = "without_meat"
                    else
                        result[0] = "non"
                })
        }
        Divider(color = Color.LightGray, thickness = 1.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            val checkedState = remember { mutableStateOf(filterStates[1]) }

            Text(
                "Острое", modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
            )
            Checkbox(
                checked = checkedState.value,
                colors = CheckboxDefaults.colors(
                    checkedColor = Orange,
                    uncheckedColor = Color.LightGray
                ),
                onCheckedChange = {
                    checkedState.value = it
                    filterStates[1] = it
                    if (it)
                        result[1] = "hot"
                    else
                        result[1] = "non"
                })
        }
        Divider(color = Color.LightGray, thickness = 1.dp)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp)
        ) {
            val checkedState = remember { mutableStateOf(filterStates[2]) }

            Text(
                "Со скидкой", modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .fillMaxWidth()
            )
            Checkbox(
                checked = checkedState.value,
                colors = CheckboxDefaults.colors(
                    checkedColor = Orange,
                    uncheckedColor = Color.LightGray
                ),
                onCheckedChange = {
                    checkedState.value = it
                    filterStates[2] = it
                    if (it)
                        result[2] = "sale"
                    else
                        result[2] = "non"
                })
        }
    }
    return result
}

@Composable
fun Navigation(viewModel: MainViewModel, cartviewModel: CartViewModel) {
    val navController = rememberNavController()
    val back = remember { mutableStateOf(false) }

    NavHost(
        navController = navController,
        startDestination = "splash_screen"
    ) {
        composable("splash_screen") {
            SplashScreen(navController = navController)
        }
        composable("splash_screen_2") {
            SplashScreen2(navController = navController)
        }
        // Main Screen
        composable("main_screen") {
            MainScreen(navController = navController, viewModel)
            back.value = false
        }
        //корзина
        composable("cart") {
            Cart(back, cartviewModel)
        }
        //поиск по названию
        composable("filter") {
            Filter(back, viewModel)
        }
    }
    if (back.value) {
        navController.navigate("main_screen")
    }
}

@Composable
fun SplashScreen(navController: NavController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(100L)
        navController.navigate("splash_screen_2")
    }
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Orange),
    ) {//не работает
//        LottieAnimation(modifier = Modifier.size(400.dp),
//            composition = composition,
//            progress = { progress },
//            )
        Image(
            painter = painterResource(id = R.drawable.logo),
            contentDescription = "Logo",
            modifier = Modifier.scale(2.5f)
        )
    }
}

@Composable
fun SplashScreen2(navController: NavController) {
    val scale = remember {
        androidx.compose.animation.core.Animatable(0f)
    }

    LaunchedEffect(key1 = true) {
        scale.animateTo(
            targetValue = 0.7f,
            animationSpec = tween(
                durationMillis = 800,
                easing = {
                    OvershootInterpolator(4f).getInterpolation(it)
                })
        )
        delay(100L)
        navController.navigate("main_screen")
    }

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(Orange)
    ) {
        Image(
            painter = painterResource(id = R.drawable.animation),
            contentDescription = "Logo",
            modifier = Modifier.scale(2.5f)
        )
    }
}

