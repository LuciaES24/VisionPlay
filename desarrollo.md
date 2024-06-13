# Desarrollo

Antes de comenzar con la parte de programación del proyecto, comencé creando el prototipo de la aplicación en Figma, para así tener una idea de cómo enfocar cada una de las pantallas y funcionalidades que debía crear.\


El desarrollo del proyecto comenzó con la creación de las pantallas de inicio de sesión y registro utilizando la autenticación de Firebase para comprobar que los datos introducidos por los usuarios son correctos.

&#x20;

A continuación, comencé a crear la pantalla principal de la aplicación en la que el usuario puede ver películas o series aleatorias. En un principio esta información se extraía directamente de la API a través de peticiones, sin embargo, para evitar tener que hacer diversas peticiones y que pueda causar mayores problemas como por ejemplo, por la conexión del usuario, decidí que era mejor obtener esta información desde la base de datos. Para esto tuve que crear un usuario administrador el cual es el encargado de actualizar esta información diariamente eliminando primeramente la información que ya hay en dicha colección y posteriormente haciendo una única petición masiva a la API e insertando la información de nuevo en la base de datos.

Además, en esta pantalla se encuentra el buscador, en el cual el usuario escribe su consulta y esta se envía a la API para encontrar los resultados.

En cuanto al tema de la navegación en la pantalla, el desplazamiento entre películas o series para que aparezca la siguiente está hecho manualmente con una variable “offset” que controla el desplazamiento que ha realizado el usuario sobre la pantalla, si es positivo pasa a la siguiente y si es negativo vuelve a la anterior.

Cada vez que el usuario accede a la aplicación las películas o series que se muestran habrán cambiado su orden para ofrecer una mejor experiencia al usuario.



Una vez obtenida la información de las películas y series, he creado una pantalla en la que el usuario la pueda ver de forma más detallada. Como la API no devuelve toda la información de una película o serie en concreto, algunos datos como las plataformas en las que se encuentra, el tráiler o las similares, se obtienen haciendo diversas peticiones a la API en el momento en el que el usuario pulsa sobre una en concreto.&#x20;

Además, para la navegación entre las similares y la actual, he creado una “Pila”, en la que se va guardando el orden en el que entra en la información de una película o serie y si pulsa el botón de volver, elimina la última de la lista y vuelve a la pantalla anterior.

Por otro lado, los géneros disponibles de películas o series están guardados en un mapa el cual guarda el identificador como clave y el género como valor, por lo que al ver la información de la misma, busca en el diccionario los identificadores que coinciden y muestra los valores en una cadena.



Para la pantalla de géneros, he utilizado la información de la base de datos de películas y series, filtrando por el género en concreto que selecciona el usuario y buscando las que coinciden con su identificador en la colección. En esta pantalla la navegación es igual que en la pantalla de inicio de películas y series.



Para obtener la información de la pantalla de cartelera, la información se recoge directamente de la API, y dependiendo de la que se pulse para ver el tráiler, hace una consulta del mismo para encontrar el enlace y mostrarlo correctamente al usuario.\


Finalmente, en la pantalla del usuario se busca en la base de datos de favoritos, las películas o series que coinciden con el usuario que está identificado actualmente en la aplicación. Desde esta pantalla puede eliminar las que tiene añadidas o volver a ver la información completa de la misma manera explicada anteriormente. Además podrá cerrar sesión, lo cual le llevará de nuevo a la pantalla de inicio de sesión por si quiere acceder con otra cuenta o registrarse con una nueva.

\
