package com.lespsan543.visionplay.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.ClickableText
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.lespsan543.apppeliculas.peliculas.ui.viewModel.LogInOrRegisterViewModel
import com.lespsan543.visionplay.app.data.util.Constants.FONT_FAMILY
import com.lespsan543.visionplay.app.navigation.Routes

/**
 * Pantalla donde el usuario podrá registrarse con un nombre, email y contraseña
 *
 * @param navController nos permite realizar la navegación entre pantallas
 * @param viewModel viewModel del que obtendremos los datos
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavController, viewModel : LogInOrRegisterViewModel){
    //Determina si la contraseña está visible o no
    var hidden by remember { mutableStateOf(true) }
    //Controla si algún dato es incorrecto para mostrar el mensaje de error
    val wrong by viewModel.wrong.collectAsState()
    //Nombre que escriba el usuario
    val name = viewModel.name
    //Email que escriba el usuario
    val email = viewModel.email
    //Contraseña que escriba el usuario
    val password = viewModel.password
    BoxWithConstraints {
        val width = maxWidth
        val height = maxHeight
        Column(modifier = Modifier
            .fillMaxSize()
            .background(Color(85, 85, 85)),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(text = "Registrarse", fontSize = 30.sp,
                modifier = Modifier.padding(top = height*0.1f), color = Color.White, fontFamily = FONT_FAMILY
            )
            Spacer(modifier = Modifier.height(height * 0.08f))
            TextField(value = name,
                onValueChange = { viewModel.writeName(it) },
                label = { Text(text = "Name...", color = Color.DarkGray, fontFamily = FONT_FAMILY) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    containerColor = Color.White,
                    textColor = Color.Black
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(height * 0.08f))
            TextField(value = email,
                onValueChange = { viewModel.writeEmail(it) },
                label = { Text(text = "Email...", color = Color.DarkGray, fontFamily =FONT_FAMILY) },
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    containerColor = Color.White,
                    textColor = Color.Black
                ),
                singleLine = true
            )
            Spacer(modifier = Modifier.height(height * 0.08f))
            TextField(value = password,
                onValueChange = { viewModel.writePassword(it) },
                label = { Text(text = "Password...",  color = Color.DarkGray, fontFamily = FONT_FAMILY) },
                colors = ExposedDropdownMenuDefaults.textFieldColors(
                    containerColor = Color.White,
                    textColor = Color.Black,
                    unfocusedIndicatorColor = Color.LightGray
                ),
                singleLine = true,
                visualTransformation =
                if (hidden) PasswordVisualTransformation() else VisualTransformation.None,
                trailingIcon = {
                    IconButton(onClick = { hidden = !hidden }) {
                        val vector = if (hidden) Icons.Filled.Visibility
                        else Icons.Filled.VisibilityOff
                        val description = if (hidden) "Ocultar contraseña" else "Revelar contraseña" //6
                        Icon(imageVector = vector, contentDescription = description)
                    }
                }
            )
            Text(
                text = "*Al menos 6 caracteres",
                color = Color.LightGray,
                style = MaterialTheme.typography.bodySmall,
                modifier = Modifier.padding(end = width*0.3f)
            )
            Spacer(modifier = Modifier.height(height * 0.08f))
            OutlinedButton(onClick = { viewModel.createUser { navController.navigate(Routes.MoviesScreen.route) } },
                modifier = Modifier
                    .height(height * 0.06f)
                    .width(width * 0.35f),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(containerColor = Color.White, contentColor = Color.Black))
            {
                Text(text = "Registrarse", fontFamily = FONT_FAMILY)
            }
            Spacer(modifier = Modifier.height(height * 0.07f))
            Row {
                Text(text = "¿Ya estas registrado?", color = Color.White, fontFamily = FONT_FAMILY)
                ClickableText(onClick = { navController.navigate(Routes.LogInScreen.route)
                                        viewModel.reset()},
                    text = AnnotatedString("Inicia sesión"),
                    style = TextStyle(
                        color = Color(135,0,0),
                        fontSize = 16.sp,
                        textDecoration = TextDecoration.Underline,
                        fontFamily = FONT_FAMILY
                    )
                )
            }
            //Se muestra si algún dato es incorrecto
            if (wrong == true){
                AlertDialog(onDismissRequest = {  },
                    confirmButton = { Button(onClick = { viewModel.closeDialog() }) {
                        Text(text = "Aceptar", fontFamily = FONT_FAMILY)
                    }
                    },
                    text = { Text(text = "Algún dato es incorrecto", fontFamily = FONT_FAMILY)}
                )
            }
        }
    }
}