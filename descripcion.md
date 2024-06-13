# Descripción

**Organización:**&#x20;

El proyecto tiene una estructura MVVM (Modelo, Vista, Vista-Modelo) con la que desacoplar las distintas capas del mismo junto con "Clean Architecture".

Esquema de "Clean Architecture":

<figure><img src=".gitbook/assets/image (19).png" alt="" width="563"><figcaption><p>Clean architecture</p></figcaption></figure>

Como podemos ver en el esquema, el proyecto está dividido en 3 capas: Data, Domain y UI.&#x20;

La primera de ellas, Data, contiene los modelos de datos que se encargan de la comunicación con la API para extraer la información junto con un archivo de "Constantes" que guarda variables que harán falta en todo el proyecto. Además, en esta capa encontramos "RetrofitAPI", una interfaz que se encarga de declarar los métodos con las consultas que se van a realizar en la API,  "APIService", donde   se define la API y se realizan las consultas para convertirlas a objetos, y por  último, "AppRepository", el cual se encarga de convertir los objetos obtenidos con la información de la API a estados con los que trabajaremos en los casos de uso y en la capa de UI. Este último archivo es el que se comunica con la capa Domain.

La segunda capa, Domain, cuenta con los casos de uso de la aplicación, recogiendo los datos del archivo "AppRepository". En esta capa, contamos con casos de uso para cada una de las funciones relacionadas con la API, cuyos archivos son: "DiscoverMovieProviderUseCase", "DiscoverMoviesUseCase", "DiscoverSerieProviderUseCase", "DiscoverSeriesUseCase", "DiscoverSimilarMoviesUseCase", "GetCineMoviesUseCase", "GetCinemaUseCase", "GetMovieGenresUseCase", "GetSerieGenresUseCase", "GetTrailerUseCase", "SearchUseCase".

Por último, la capa UI contiene todo lo relacionado con la interfaz de usuario. En esta capa se encuentran todos los archivos "Screen" con las diferentes pantallas, los estados, encargados de guardar la información convertida de los modelos, el "ViewModel" con todas las funciones relacionadas con las pantallas y con el usuario, y el cual se comunica directamente con la capa Domain a través de los casos de uso, y por último algunos componentes.

**Funcionalidades creadas:**

1. **Buscar películas populares en la API:** Hace una petición a la API en la que devuelve una lista de 20 películas populares, cada una con su información.
2. **Buscar series populares en la API:** Hace una petición a la API en la que devuelve una lista de 20 series populares, cada una con su información.
3. **Buscar plataformas en las que se encuentra una película en la API:** Hace una petición a la API en la que devuelve una lista con todas las plataformas en las que pueden haber películas.
4. **Buscar plataformas en las que se encuentra una serie en la API:** Hace una petición a la API en la que devuelve una lista con todas las plataformas en las que pueden haber series.
5. **Buscar películas y series similares a partir de otra en la API:** Obtiene el identificador de una película o serie y busca en la API las que sean similares.
6. **Buscar las películas que se encuentran actualmente en el cine en la API:** Hace una petición a la API en la que obtiene la lista de películas que se encuentran actualmente en el cine.
7. **Buscar los géneros que pueden tener las películas en la API:** Hace una petición a la API en la que devuelve una lista con todos los géneros posibles que puede tener una película.
8. **Buscar los géneros que pueden tener las series en la API:** Hace una petición a la API en la que devuelve una lista con todos los géneros posibles que puede tener una serie.
9. **Permitir que el usuario realice búsquedas y traer los resultados de la API:** Guarda la búsqueda que quiere realizar el usuario y realiza la petición a la API para ver los resultados que coinciden.
10. **Buscar el tráiler de una película o serie en la API de YouTube:** Busca los videos que coinciden a partir del título de la película o serie que se ha seleccionado y extrae el identificador del video para mostrarlo.
11. **Permitir al usuario ver las películas y series que se encuentran en la base de datos y ver su información:** Extrae todas las películas o series de la base de datos filtrando por tipo dependiendo de la pantalla en la que se encuentra.
12. **Permitir que el usuario añada a favoritos las películas o series que quiera y pueda visualizarlas o eliminarlas de su lista:** Añade a la base de datos la película o serie que selecciona el usuario. Además puede eliminarlas de la base de datos actualizándose automáticamente la lista en la pantalla.
13. **Permitir que el usuario pueda realizar comentarios en la película o serie que quiera:** Añade el comentario que ha escrito el usuario a la base de datos, enlazándolo al usuario que lo ha realizado y a la película o serie a la que pertenece.
14. **Permitir al usuario buscar películas y series a partir de un género en concreto:** Extrae todas las películas o series de la base de datos filtrando por el género seleccionado.

Además, la aplicación cuenta con un usuario administrador el cual puede actualizar la base de datos en la que se encuentran las películas y series que se han recogido de la API. Al actualizar la base de datos, primero elimina las películas y series que ya estaban y hace una nueva búsqueda masiva en la API.
