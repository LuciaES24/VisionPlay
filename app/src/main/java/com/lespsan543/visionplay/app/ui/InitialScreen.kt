package com.lespsan543.visionplay.app.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.lespsan543.visionplay.app.navigation.Routes
import com.lespsan543.visionplay.app.ui.viewModel.VisionPlayViewModel

/**
 * Función encargada de la navegación al iniciar la aplicación
 *
 * @param navController nos permite realizar la navegación entre pantallas
 * @param visionPlayViewModel viewModel del que obtendremos los datos
 */
@Composable
fun InitialScreen(navController: NavController, visionPlayViewModel: VisionPlayViewModel){
    LaunchedEffect(Unit){
        if (!FirebaseAuth.getInstance().currentUser?.email.isNullOrEmpty()){
            //Si el usuario ya ha iniciado sesión
            navController.navigate(Routes.MoviesScreen.route)
            visionPlayViewModel.findUserInDB()
        }else{
            navController.navigate(Routes.LogInScreen.route)
        }
    }
}