package com.abshtyfikant.fancal

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.abshtyfikant.fancal.data.BottomBarScreen
import com.abshtyfikant.fancal.screens.FavouritesScreen
import com.abshtyfikant.fancal.screens.FollowingScreen
import com.abshtyfikant.fancal.screens.NotificationsScreen
import com.abshtyfikant.fancal.screens.TimelineScreen

@Composable
fun BottomNavGraph(navController: NavHostController){
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Timeline.route
    ){
        composable(route = BottomBarScreen.Timeline.route){
            TimelineScreen()
        }
        composable(route = BottomBarScreen.Following.route){
            FollowingScreen()
        }
        composable(route = BottomBarScreen.Favourites.route){
            FavouritesScreen()
        }
        composable(route = BottomBarScreen.Notifications.route){
            NotificationsScreen()
        }
    }
}