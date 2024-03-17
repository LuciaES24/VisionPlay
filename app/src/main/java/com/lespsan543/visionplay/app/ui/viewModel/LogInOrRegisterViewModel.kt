package com.lespsan543.apppeliculas.peliculas.ui.viewModel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

/**
 * ViewModel responsable del inicio de sesión y el registro del usuario en la base de datos
 *
 * @property auth Instancia de FirebaseAuth utilizada para obtener el usuario actual
 * @property firestore Instancia de FirebaseFirestore utilizada para operaciones en la base de datos
 * @property email variable en la que vamos a guardar el email que escriba el usuario
 * @property password variable en la que vamos a guardar la contraseña que escriba el usuario
 * @property name variable en la que vamos a guardar el nombre que escriba el usuario
 * @property _wrong flujo de datos del booleano que determina si alguno de los datos está incorrecto para mostrar el error
 * @property wrong estado público del boleano que controla los errores
 */
class LogInOrRegisterViewModel : ViewModel() {
    private val auth: FirebaseAuth = Firebase.auth
    private val firestore = Firebase.firestore

    var email by mutableStateOf("")
        private set
    var password by mutableStateOf("")
        private set
    var name by mutableStateOf("")
        private set

    private var _wrong = MutableStateFlow(false)
    var wrong : StateFlow<Boolean> = _wrong

    /**
     * Crea al usuario para darle de alta en la autenticación
     *
     * @param onSuccess si se crea correctamente navega a la siguiente pantalla
     */
    fun createUser(onSuccess: () -> Unit){
        viewModelScope.launch {
            try {
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            saveUser(name)
                            onSuccess()
                        } else {
                            _wrong.value = true
                        }
                    }
            } catch (e: Exception){
                _wrong.value = true
            }
        }
    }

    /**
     * Guarda un nuevo usuario en la base de datos
     *
     * @param username nombre del usuario
     */
    private fun saveUser(username: String){
        val id = auth.currentUser?.uid
        val email = auth.currentUser?.email

        viewModelScope.launch(Dispatchers.IO) {
            val user = hashMapOf(
                "id" to id.toString(),
                "email" to email.toString(),
                "name" to username,
                "password" to password
            )
            firestore.collection("Users")
                .add(user)
        }
    }

    /**
     * Permite iniciar sesión al usuario y controla que los datos introducidos corresponden con los de la base de datos
     *
     * @param onSuccess si se crea correctamente navega a la siguiente pantalla
     */
    fun logIn(onSuccess: () -> Unit){
        viewModelScope.launch {
            try {
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { log ->
                        if (log.isSuccessful) {
                            onSuccess()
                        } else {
                            _wrong.value = true
                        }
                    }
                reset()
            } catch (e: Exception){
                _wrong.value = true
            }
        }
    }

    /**
     * Resetea la información de los campos email, nombre y contraseña
     */
    fun reset(){
        email = ""
        name = ""
        password = ""
    }

    /**
     * Cambia el booleano que controla que se muestre el diálogo
     */
    fun closeDialog(){
        _wrong.value = false
    }

    /**
     * Guarda en la variable el email que escribe el usuario
     *
     * @param email email del usuario
     */
    fun writeEmail(email:String){
        this.email = email
    }

    /**
     * Guarda en la variable el nombre que escribe el usuario
     *
     * @param name nombre del usuario
     */
    fun writeName(name:String){
        this.name = name
    }

    /**
     * Guarda en la variable la contraseña que escribe el usuario
     *
     * @param password contraseña del usuario
     */
    fun writePassword(password:String){
        this.password = password
    }
}