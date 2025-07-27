package com.example.yemeklerroom.pages

import android.annotation.SuppressLint
import android.app.Application
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.yemeklerroom.R
import com.example.yemeklerroom.entity.Yemekler
import com.example.yemeklerroom.viewmodel.MainPageViewModel
import com.example.yemeklerroom.viewmodel.MainPageViewModelFactory
import com.example.yemeklerroom.viewmodel.RecordPageViewModel
import com.example.yemeklerroom.viewmodel.RecordPageViewModelFactory

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun RecordPage(navController: NavController){
    val context = LocalContext.current
    val viewModel: RecordPageViewModel = viewModel(
        factory = RecordPageViewModelFactory(context.applicationContext as Application)
    )
    var yemeklerListesi = viewModel.yemeklerListesi.observeAsState(listOf())
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Yemek Ekle") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.topAppBarReng),
                    titleContentColor = Color.White
                )
            )
        },
        content = {
            var tfyemek_ad = remember { mutableStateOf("") }
            var tfyemek_fiyat = remember { mutableStateOf("") }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFF7F7F7))
                    .padding(24.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    verticalArrangement = Arrangement.spacedBy(20.dp)
                ) {
                    Text(
                        text = "Yemek Ekle",
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold
                    )

                    OutlinedTextField(
                        value = tfyemek_ad.value,
                        onValueChange = { tfyemek_ad.value = it },
                        label = { Text("Yemek Adı") },
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    OutlinedTextField(
                        value = tfyemek_fiyat.value,
                        onValueChange = { tfyemek_fiyat.value = it },
                        label = { Text("Yemek Fiyatı") },
                        shape = RoundedCornerShape(16.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.fillMaxWidth()
                    )

                    Button(
                        onClick = {
                            viewModel.yemekEkle(tfyemek_ad.value, tfyemek_fiyat.value.toInt())
                            navController.navigate("mainPage")
                        },
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF4CAF50),
                            contentColor = Color.White
                        )
                    ) {
                        Text("Ekle", fontSize = 18.sp)
                    }
                }
            }
        }
    )
}