# Pruebas

Para realizar las pruebas o test unitarios del código, he añadido las siguientes dependencias al proyecto para poder crearlos:

```
//Realizar test
    testImplementation("junit:junit:4.+")
    testImplementation("io.mockk:mockk:1.12.2")
```

Una vez añadidas las dependencias al proyecto, he creado un test para cada caso de uso que hay en el proyecto, creando una instancia del caso de uso en concreto y mockeando el repositorio del que recoge las funciones de la API que debe invocar. Sin embargo, en los casos de uso, al tener que ver directamente con la información que se recoge de la API, no he sabido implementarlos correctamente, por lo que fallan, pero en el código funcional del proyecto a la hora de utilizar la aplicación, las operaciones funcionan correctamente.&#x20;

<figure><img src=".gitbook/assets/image (1).png" alt=""><figcaption><p>Test de los casos de uso</p></figcaption></figure>

Ejemplo de test en el que la API no devuelve datos:

```kotlin
class DiscoverMoviesUseCaseTest{

    @RelaxedMockK
    private lateinit var appRepository: AppRepository

    private lateinit var discoverMoviesUseCase: DiscoverMoviesUseCase
    @Before
    fun onBefore() {
        MockKAnnotations.init(this)
        discoverMoviesUseCase = DiscoverMoviesUseCase()
    }

    @Test
    fun apiReturnsNothing() = runBlocking {
        //Given
        coEvery { appRepository.discoverMovies(0) } returns MovieOrSerieResponseState()
        //When
        discoverMoviesUseCase(0)
        //Then
        coVerify(exactly = 1) { appRepository.discoverMovies(0) }
    }
}

```

**Test de usabilidad:**

* Mapa de calor: He sometido algunas pantallas al mapa de calor y estos son los resultados obtenidos, los cuales en general están bastante cercanos a lo esperado ya que son las zonas en las que más puede pulsar el usuario de la pantalla.

<div align="center" data-full-width="false">

<figure><img src=".gitbook/assets/image (22).png" alt="" width="207"><figcaption><p>Pantalla inicio</p></figcaption></figure>

 

<figure><img src=".gitbook/assets/image (27).png" alt="" width="205"><figcaption><p>Cartelera</p></figcaption></figure>

</div>

<figure><img src=".gitbook/assets/image (28).png" alt="" width="210"><figcaption><p>Pantalla información</p></figcaption></figure>

* Test de los 5 segundos: A través del test de los 5 segundos compruebo si la interfaz de mi aplicación se puede entender a simple vista y puede realizarlo a través del siguiente [enlace](https://app.lyssna.com/do/430710ac8ff/b26a).

En cuanto a las respuestas obtenidas en el test, en general son bastante acordes con el resultado que esperaba. Creo que las pantallas se entienden bastante bien su finalidad aunque la de cartelera ha podido confundir ya que he utilizado imágenes del prototipo y todas las películas que aparecen son la misma. Los usuarios han entendido bastante bien cómo se realiza el desplazamiento en la pantalla principal para que aparezcan nuevas películas o series y también han entendido el formato para la puntuación de las mismas.

\
