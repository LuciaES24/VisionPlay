package com.lespsan543.visionplay.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.lespsan543.visionplay.app.navigation.Routes
import com.lespsan543.visionplay.app.ui.viewModel.MoviesOrSeriesViewModel

@Composable
fun InitialScreen(navController: NavController, moviesOrSeriesViewModel: MoviesOrSeriesViewModel){
    LaunchedEffect(Unit){
        if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navController.navigate(Routes.MoviesScreen.route)
            moviesOrSeriesViewModel.findUserInDB()
        }else{
            navController.navigate(Routes.LogInScreen.route)
        }
    }
}