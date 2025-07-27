package com.example.yemeklerroom

import android.annotation.SuppressLint
import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
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
                title = {Text("Yemekler")},
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = colorResource(R.color.topAppBarReng),
                    titleContentColor = Color.White
                )
            )
        },
        content = {pd->
            Column(verticalArrangement = Arrangement.SpaceEvenly,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxSize()) {
                TextField(value = tf_ad.value, onValueChange = {tf_ad.value = it})
                TextField(value = tf_fiyat.value, onValueChange = {tf_fiyat.value = it})
                Button(onClick = {
                    viewModel.yemekEkle(tf_ad.value, tf_fiyat.value.toInt())
                    viewModel.yemekGetir()
                    tf_ad.value = ""
                    tf_fiyat.value = ""
                }
                ) {
                    Text("EKLE")
                }

            }
            LazyColumn(modifier = Modifier.padding(pd)) {
                items(
                    count = yemeklerListesi.value.count(),
                    itemContent = {
                        var yemek = yemeklerListesi.value[it]
                        Card(modifier = Modifier.fillMaxWidth().padding(all = 4.dp)) {
                            Row {
                                Text("${yemek.yemek_id}")
                                Text("${yemek.yemek_ad}")
                                Text("${yemek.yemek_fiyat}")
                            }
                            Row(modifier = Modifier.clickable{
                                Log.i("tiklama", "${yemek.yemek_ad}")
                            }) {
                                Row(modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.Center) {

                                }
                            }
                        }
                        Button(onClick = {
                            viewModel.yemekSil(yemek.yemek_id)
                            viewModel.yemekGetir()
                        }
                        ) {
                            Text("SIL")
                        }

                        Button(onClick = {
                            tf_ad.value = yemek.yemek_ad
                            tf_fiyat.value = yemek.yemek_fiyat.toString()
                        }
                        ){
                            Text("GETIR")
                        }

                        Button(onClick = {
                           viewModel.yemekGuncelle(yemek.yemek_id,
                               tf_ad.value,
                               tf_fiyat.value.toInt())
                           viewModel.yemekGetir()
                        }
                        ){
                            Text("GUNCELLE")
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