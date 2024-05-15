package com.lespsan543.visionplay.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.lespsan543.visionplay.app.navigation.Routes
import com.lespsan543.visionplay.app.ui.viewModel.VisionPlayViewModel

@Composable
fun InitialScreen(navController: NavController, visionPlayViewModel: VisionPlayViewModel){
    LaunchedEffect(Unit){
        if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            navController.navigate(Routes.MoviesScreen.route)
            visionPlayViewModel.findUserInDB()
        }else{
            navController.navigate(Routes.LogInScreen.route)
        }
    }
}