package com.example.littlelemoncapstone.composables

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemoncapstone.navigation.Home
import com.example.littlelemoncapstone.navigation.Onboarding
import com.example.littlelemoncapstone.navigation.Profile

@Composable
fun NavigationComposable(navController: NavHostController, context: Context) {
    val sharedPreferences = context.getSharedPreferences("Little Lemon", Context.MODE_PRIVATE)
    var startDestination = Onboarding.route

    if(sharedPreferences.getBoolean("userRegistered", false)){
        startDestination = Home.route
    }

    NavHost(navController = navController, startDestination = startDestination){
        composable(Onboarding.route){
            Onboarding(context, navController)
        }
        composable(Home.route){
            Home(context, navController)
        }
        composable(Profile.route){
            Profile(context, navController)
        }
    }
}