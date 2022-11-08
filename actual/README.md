## LABORATORIO 2022 ALGORITMICA Y PROGRAMACION II

### Introduccion
Programa de Análisis de Redes Sociales que se centra en el descubrimiento de patrones de interacción entre actores sociales de una red social.

### Planteo del problema
Este sistema permite conocer y explorar relaciones entre usuarios para sugerir nuevas entre los mismos basados en sus amistades, gustos y hábitos. Las relaciones de amistad deben ser unilaterales, osea que uno es amigo del otro. Provee funciones para visualizar a los usuarios más influyentes (con más amigos), el promedio de amigos por usuario, y el camino más corto (o más reciente) entre 2 usuarios, en términos del tiempo que llevan siendo amigos. Además deberá ofrecer una interfaz de usuario amigable y fácil de usar. Este programa además, debe ser robusto y escalable.

### Analisis de las estructuras seleccionadas
Como principal estructura de datos para la red social usamos un grafo no dirigido, así podremos representar las personas como nodos y las relaciones como arcos. Y al ser no dirigido podemos recrear la realidad de manera más precisa. Ademas se usara un mapa para guardar los usuarios, donde la clave es el codigo de usuario y el valor es el usuario. Tambien se usaran 

### Diagrama de clases



### Implementacion de la solucion
Para desarrollar este sistema se usará una estructura con capas. Haciendo que cada clase sea independiente, y no dependa de otra para funcionar. Además se usarán archivos de texto para cargar datos y configuraciones. 
Para la implementación se usaron, TADs como grafos, listas y mapas para manejar los datos. También se usarán librerías que provee java: Java.Swing
La libreria aportada por Apache: log4j

### Manual de funcionamiento 
Cuando se procede a correr la aplicación, aparecerá una ventana donde se puede elegir entre algunas de las siguientes:
 Usuarios, los usuarios de la red;
 Usuarios Densamente Conectados, el nombre de los usuarios con la interaccion diaria (sumatoria de todas las interacciones) ordenados de forma descendiente;
 Los amigos de un determinado usuario, sus nombres y el tiempo siendo amigos;
 Sugerencias de amistad: los amigos de los amigos de un determinado usuario;
 Grado Medio: cantidad de amigos dividida por la cantidad de usuarios;
 Centralidad: los usuarios ordenados por la cantidad de amistades de forma descendiente;
 Antiguedad: el camino mas corto, considerando el tiempo de relacion, de un usuario a otro;


Existe una barra de menu superior en la que se encuentra el menu "Opcion" con 2 opciones:
"Usuarios": que despliega una lista de usuarios que se proda insertar modificar y borrar;
"Relaciones": que despliega una lista de usuarios que se proda insertar modificar y borrar;

Si se borrara un Usuario que tenga relacion alguna con otro Usuario se borra dicha Relacion.
Cuenta con un boton "actualizar" para volver a cargar la lista de relaciones.

Ingreso de datos usuario:
ID es numerico (max 5 digitos);
Los nombres comienzan con mayuscula, y no llevan espacio;
La fecha es con el formato: yyyy-mm-dd; 
Sexo: m,f o x (en minuscula o MAYUSCULA);
Localidad no tiene restricciones;
Nivel Academ.:primario, secundario, terciario, universitario o nulo (en minuscula o MAYUSCULA);

Ingreso de datos relacion:
ID: de los usuarios ya existentes;
interaccion-Likes: numeros enteros;
La fecha es con el formato: yyyy-mm-dd; 



### Errores detectados
1) Al querer borrar una relacion: primero se debe presionar el boton "borrar" y salir con el boton "cancelar" 
2) posterior al borrado de una relacion, no se borra de la lista de relaciones, se usa un boton actualizar para volver a cargar las relaciones 

### Lote de pruebas
En los archivo relacionRepetidos*.txt hay relaciones repetidas.
ejem: Relacion(a,b) y Relacion(a,b); Relacion(a, b) y Relacion(b, a)

En el archivo usuarioRepetido.txt existen usuarios repetidos;

Los archivos relaciones.txt y usuarios.txt destinados para la Aplicacion


### Posibles mejoras y extensiones
Mostrar de manera más clara el camino más corto en la función Antigüedad;
Un comboBox para las enumeraciones;
Metodos mas eficientes en el paquete GUI;
Una interfaz mas adaptable a distintos gestores de ventanas;
Uso de BD;

Incluir una representación visual del grafo principal, y en la función Antigüedad.

### Conclusiones
Podemos concluir que las estructuras de datos, principalmente los grafos y los mapas en este caso, son suficientemente poderosos para modelar de manera precisa el problema planteado. Además, pese a las posibles mejoras que se podrían implementar, usando solamente Java, se pudo llegar a un programa decente que cumple con su objetivo.
