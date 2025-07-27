package com.example.yemeklerroom

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.yemeklerroom.entity.Yemekler
import com.example.yemeklerroom.ui.theme.YemeklerRoomTheme
import com.example.yemeklerroom.viewmodel.MainPageViewModel
import com.example.yemeklerroom.viewmodel.MainPageViewModelFactory
import kotlinx.coroutines.Job

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            YemeklerRoomTheme {
                MainPage()
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun MainPage(){
    val tf_id = remember { mutableStateOf("") }
    val tf_ad = remember { mutableStateOf("") }
    val tf_fiyat = remember { mutableStateOf("") }

    val context = LocalContext.current
    val viewModel: MainPageViewModel = viewModel(
        factory = MainPageViewModelFactory(context.applicationContext as Application)
    )
    var yemeklerListesi = viewModel.yemeklerListesi.observeAsState(listOf())
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Yemekler") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.topAppBarReng),
                    titleContentColor = Color.White
                ),
                actions = {
                    IconButton(onClick = {
                        // EKLE işlemi burada olacak
                        viewModel.yemekEkle(
                            tf_ad.value,
                            tf_fiyat.value.toIntOrNull() ?: 0
                        )
                        viewModel.yemekGetir()
                    }) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_ekle),
                            contentDescription = "Ekle",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        content = {pd->

            LazyColumn(modifier = Modifier.padding(pd)) {
                items(
                    count = yemeklerListesi.value.count(),
                    itemContent = {
                        val yemek = yemeklerListesi.value[it]

                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(8.dp),
                            shape = RoundedCornerShape(16.dp),
                            elevation = CardDefaults.cardElevation(6.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFFF8F8F8))
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(16.dp)
                            ) {
                                // ÜST: Resim ve Bilgiler
                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.Start
                                ) {
                                    Image(
                                        painter = painterResource(id = R.drawable.yemek),
                                        contentDescription = yemek.yemek_ad,
                                        modifier = Modifier
                                            .size(72.dp)
                                            .clip(RoundedCornerShape(12.dp))
                                    )

                                    Spacer(modifier = Modifier.width(16.dp))

                                    Column {
                                        Text(
                                            text = yemek.yemek_ad,
                                            style = MaterialTheme.typography.titleMedium,
                                            color = Color.Black
                                        )
                                        Text(
                                            text = "${yemek.yemek_fiyat} ₺",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = Color.Gray
                                        )
                                    }
                                }

                                Spacer(modifier = Modifier.height(12.dp))

                                // ALT: Şık Butonlar
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ) {
                                    // SİL
                                    Button(
                                        onClick = {
                                            viewModel.yemekSil(yemek.yemek_id)
                                            viewModel.yemekGetir()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFD32F2F)),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_sil),
                                            contentDescription = "Sil",
                                            tint = Color.White
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("SİL", color = Color.White)
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    // GETİR
                                    Button(
                                        onClick = {
                                            tf_ad.value = yemek.yemek_ad
                                            tf_fiyat.value = yemek.yemek_fiyat.toString()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF1976D2)),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_getir),
                                            contentDescription = "Getir",
                                            tint = Color.White
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("GETİR", color = Color.White)
                                    }

                                    Spacer(modifier = Modifier.width(8.dp))

                                    // GÜNCELLE
                                    Button(
                                        onClick = {
                                            viewModel.yemekGuncelle(
                                                yemek.yemek_id,
                                                tf_ad.value,
                                                tf_fiyat.value.toInt()
                                            )
                                            viewModel.yemekGetir()
                                        },
                                        colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF388E3C)),
                                        shape = RoundedCornerShape(12.dp),
                                        modifier = Modifier.weight(1f)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.ic_guncelle),
                                            contentDescription = "Güncelle",
                                            tint = Color.White
                                        )
                                        Spacer(modifier = Modifier.width(4.dp))
                                        Text("GÜNCELLE", color = Color.White)
                                    }
                                }
                            }
                        }
                    }
                )
            }


        }
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    YemeklerRoomTheme {
        MainPage()
    }
}