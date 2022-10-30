## LABORATORIO 2022 ALGORITMICA Y PROGRAMACION II

### Introduccion
Programa de Análisis de Redes Sociales que se centra en el descubrimiento de patrones de interacción entre actores sociales de una red social.

### Planteo del problema
Este sistema permite conocer y explorar relaciones entre usuarios para sugerir nuevas entre los mismos basados en sus amistades, gustos y hábitos. Las relaciones de amistad deben ser unilaterales, osea que uno es amigo del otro. Provee funciones para visualizar a los usuarios más influyentes (con más amigos), el promedio de amigos por usuario, y el camino más corto (o más reciente) entre 2 usuarios, en términos del tiempo que llevan siendo amigos. Además deberá ofrecer una interfaz de usuario amigable y fácil de usar. Este programa además, debe ser robusto y escalable.

### Analisis de las estructuras seleccionadas
Como principal estructura de datos para la red social usamos un grafo no dirigido, así podremos representar las personas como nodos y las relaciones como arcos. Y al ser no dirigido podemos recrear la realidad de manera más precisa. Ademas se usara un mapa para guardar los usuarios, donde la clave es el codigo de usuario y el valor es el usuario. Tambien se usaran 

### Diagrama de clases
![UML de clases](https://i.postimg.cc/m2fGg744/umltrabajofinal.png)

### Implementacion de la solucion
Para desarrollar este sistema se usará una estructura con capas. Haciendo que cada clase sea independiente, y no dependa de otra para funcionar. Además se usarán archivos de texto para cargar datos y configuraciones. 
Para la implementación se usaron, TADs como grafos, listas y mapas para manejar los datos. También se usarán librerías que provee java.

### Manual de funcionamiento
El ingreso de datos se realiza a través de archivos de texto especificados en el archivo "config.properties". 
Cuando se procede a correr la aplicación, aparecerá un cuadro donde se puede elegir entre algunas de las siguientes:
1: Para ver todos los usuarios. 
2: Para ver el grado medio (cantidad de conexiones promedio de cada usuario). 
3: Para ver los usuarios más influyentes. 
4: Y proceda a ingresar los códigos de 2 usuarios, para ver el camino más corto entre 2 usuarios. 
-1: Para salir del programa.
Además para cambiar de archivos de relaciones o usuarios, debera cambiar la opción en el archivo "config.properties".

### Errores detectados
La aplicación dejará de funcionar si se deja la opción vacía o si se presiona cancelar en el menú principal. Este es el único error conocido hasta el momento, el resto de las excepciones están contempladas.

### Lote de pruebas
En el archivo de relations1.txt el usuario 101 no tiene amigos. Esta excepción está contemplada. Para usar este archivo es necesario usar también el archivo users1.txt.

### Posibles mejoras y extensiones
Mostrar de manera más clara el camino más corto en la función Antigüedad.
Utilizar una interfaz más moderna como JavaFx, la cual tiene mejoras visuales, y mejor integración con el sistema operativo. 
Incluir una representación visual del grafo principal, y en la función Antigüedad.
Utilizar botones para seleccionar cada opción. 
Utilizar una lista desplegable para seleccionar los usuarios.

### Conclusiones
Podemos concluir que las estructuras de datos, principalmente los grafos y los mapas en este caso, son suficientemente poderosos para modelar de manera precisa el problema planteado. Además, pese a las posibles mejoras que se podrían implementar, usando solamente Java, se pudo llegar a un programa decente que cumple con su objetivo.