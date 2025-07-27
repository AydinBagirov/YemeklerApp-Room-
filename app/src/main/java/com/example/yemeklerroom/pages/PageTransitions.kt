package com.example.yemeklerroom.pages

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.yemeklerroom.entity.Yemekler
import com.google.gson.Gson

@Composable
fun PageTransitions(){
    val navController = rememberNavController()
    NavHost(navController, startDestination = "mainPage"){
        composable("mainPage"){
            MainPage(navController)
        }
        composable("recordPage"){
            RecordPage(navController)
        }
        composable("updatePage/{yemek}", arguments = listOf(
            navArgument("yemek"){type = NavType.StringType}
        )){
            val yemek = it.arguments?.getString("yemek")!!
            val yemekJson = Gson().fromJson(yemek, Yemekler::class.java)
            UpdatePage(yemekJson, navController)
        }
    }
}