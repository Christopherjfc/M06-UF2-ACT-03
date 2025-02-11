# M06-UF2-ACT-03

> **Nota:** ejecute **chcp 65001** en la consola donde se ejecutará el programa para habilitar el UTF-8 temporalmente (si no lo tenía activado)

# Main

Para poder gestionar las tablas de la base de datos, se utilizará este prompt personalizado, que nos redijirá a la tabla que desea el usuario. <br>
> **Nota:** la opción para salir o ir a un paso atrás siempre será -> **;**

<div>
  <img src="img/Prompt_inicial.png" alt="Prompt main" width="300"> 
</div>
<br>

# DEPARTAMENT

Al selecionar la opción 1 en el main, entrará a la tabla Departament.

<div>
  <img src="img/departament/Prompt departament.png" alt="Prompt main" width="300"> 
</div>
<br>

- **CREAR** 

Para crear un departamento solo basta con ponerle un nombre. Si el nombre introducido está vacío, volverá a esperar hasta que se le asigne uno. 
<div>
  <img src="img/departament/Crea departament+Error.png" alt="Prompt main" width="300"> 
</div>
<br>

- **ENCUENTRA**

Para encontrar la ID que se necesita, primero se mostrará un listado con todos los departamentos existentes. Si no exite ninguno, volverá al prompt de Departament.

Si existen registros se le pedirá al usuario que introduzca la ID del departamento que necesite adquerir, si la ID que se introduce no es la esperada, se manejará la excepción y volverá a esperar hasta que se le de una ID correcta. <br>

Por último, se mostrará el nombre del departamento.
<div>
  <img src="img/departament/encuentra_dep+Error.png" alt="Encuentra departamento" width="350"> 
</div>
<br>

- **MODIFICA**

Al escoger la opción de modificar un departamento, se llamará al método que muestra todos los departamentos y luego a otro método manejará las excepciones hasta que se inserte la ID correcta.

Como la tabla Departament solo tiene una columna, que es **nombre**, se le pedirá al usuario uno nuevo (no acepta nombres vacíos).

Si todo la modificación se hace correctamente, se mostrará un mensaje de confirmación.
<div>
  <img src="img/departament/modifica_dep+Error.png" alt="Modifica + errores" width="350"> 
</div>
<br>

- **REMOVE**
  
Al escoger la opción de eliminar, se muestran todos los registros existentes y se ecogerá la ID del departamento que el usuario desea eliminar.

Al escoger una ID existente, se le pedirá al usuario la confirmación para eliminar el departamento. <br> 
Si la respuesta es **"y"**, se procederá a la eliminación y mostrará un mensaje de confirmación. 

<div>
  <img src="img/departament/removeHecho.png" alt="Remove con éxito" width="350"> 
</div>
<br>

Si la respuesta es **"n"** como en este caso, se cancelará la eliminación del departamento seleccionado.

<div>
  <img src="img/departament/removeCancelado.png" alt="Remove cancelado" width="350"> 
</div>
<br>

- **EXCEPTION DUPLICATE**

> **Nota:** logré manejar la excepción de duplicado, pero no pude desaparecer el log de Error de duplicado del sqlExceptionHelper por consola.

Cuando se intenta crear un departamento con el mismo nombre, como en este caso, creo un departamento llamado **"Ingenieria"** y de seguido vuelvo a crear un nuevo departamento con el mismo nombre. Manejo la excepción de duplicado lanzando un mensaje de error, mencionando que ya existe un departamento con el mismo nombre.

<div>
  <img src="img/departament/Exception-duplicate.png" alt="Error: duplicado en creación" width="550"> 
</div>
<br>

En otro caso distinto, como es en la opción de modificar el nombre de un departamento a otro ya existente. En esta caso quise cambiar el nombre de **"Ingenieria"** a **"DAM"** y se lanza el mensaje de error creado cuando se detecta una duplicación.

<div>
  <img src="img/departament/Exception-duplicate-Modify.png" alt="Error: duplicado en modificación" width="550"> 
</div>
<br>

# EMPLEAT

Al selecionar la opción 2 en el main, entrará a la tabla Empleat.

<div>
  <img src="img/empleat/Prompt_Empleat.png" alt="Promt Empleat" width="300"> 
</div>
<br>

- **CREAR** 

Para poder crear un empleado, se necesitará obigatoriamente un departamento debido a que existe una relación **OneToMany** entre esatas entidades.

Si no existe, se le pedirá al usuario que registre uno.

<div>
  <img src="img/empleat/crearDepEmpleado.png" alt="Promt Empleat" width="450"> 
</div>
<br>

Si existen, se mostrarán todos y se le pedirá al usuario que introduzca la ID que desee añadir al empleado. <br>
Seguidamente se procederá a la creación del empleado, llenando así las columnas que se le piden, que son: **nombre**, **dni**, **correo** y **teléfono**. <br>
Cada una de las columnas están controladas bajo condiciones con regex.

Por último, se muestra un mensaje de confirmación.

<div>
  <img src="img/empleat/CreaEmpleado+Error.png" alt="Promt Empleat" width="450"> 
</div>
<br>

- **ENCUENTRA**

Para encontrar la ID del empleado que se desea obtener, se llamará al método que muestra todos los empleados y a otro que escoge un empleado por su ID.

Cuando se escoja una ID correcta, se mostrará un toString() de esa entidad.
<div>
  <img src="img/empleat/EncuentraEmpl+Error.png" alt="Promt Empleat" width="650"> 
</div>
<br>

- **MODIFICA**

Para modificar un empleado se mostrarán todos los empleados y se escogerá al empleado que se desea modificar por su ID.

En todas las imágenes no muestro las pruebas de error, ya que a la hora de crear un empleado mencioné y mostré que todas las columnas están bajo unas condiciones de regex.

En estas secuencias de imágenes mostraré los cambios que iré haciendo a un empleado ya registrado.

En el primer cambio se modificará el nombre del empleado, de **"Christopher"** a **"Herlin"**.
<div>
  <img src="img/empleat/modifica/Nombre.png" alt="Promt Empleat" width="650"> 
</div>
<br>

En el segundo cambio se modificará el DNI del empleado, de **"12345678A"** a **"32165498F"**.

<div>
  <img src="img/empleat/modifica/DNI.png" alt="Promt Empleat" width="650"> 
</div>
<br>

En el tercer cambio se modificará el correo del empleado, de **"Christopher@hibernate.com"** a **"Herlin@hibernate.cat"**.

<div>
  <img src="img/empleat/modifica/correo.png" alt="Promt Empleat" width="650"> 
</div>
<br>

En el cuarto cambio se modificará el teléfono del empleado, de **"123456789"** a **"321987456"**.
<div>
  <img src="img/empleat/modifica/telefono.png" alt="Promt Empleat" width="650"> 
</div>
<br>

En el quinto cambio se modificará el departamento del empleado, de **"5"** a **"6"**.

<div>
  <img src="img/empleat/modifica/Departamento.png" alt="Promt Empleat" width="650"> 
</div>
<br>

En el sexto cambio se modificarán todos las columnas del empleado.
<div>
  <img src="img/empleat/modifica/todos(1).png" alt="Promt Empleat" width="650"> 
</div>
<br>

Por último, para mostrar una prueba de los cambios, selecciono la opción de encuentra empleado para mostrar el empleado con todas las columnas modificadas con éxito.
<div>
  <img src="img/empleat/modifica/todos(2).png" alt="Promt Empleat" width="650"> 
</div>
<br>

- **REMOVE**
Al escoger la opción de eliminar, se llama a 2 métodos, uno que muestra todos los empleados existentes y otro que escoge un empleado por su ID.

Al escoger una ID existente, se le pedirá al usuario la confirmación para eliminar el empleado. <br> 
Si la respuesta es **"y"**, se procederá a la eliminación y mostrará un mensaje de confirmación. 

<div>
  <img src="img/empleat/removeHecho.png" alt="Promt Empleat" width="450"> 
</div>
<br>

Si la respuesta es **"n"** como en este caso, se cancelará la eliminación del empleado seleccionado.

<div>
  <img src="img/empleat/removeCancelado.png" alt="Promt Empleat" width="450"> 
</div>
<br>

- **ASIGNA TAREA A EMPLEADO**

Al tener una relación ManyToMany con la entidad Tasca, se deberá añadir una tarea a un empleado y viceversa en un mismo método, cuyo método que ya tienen creadas estas dos entidades.

Al tener este tipo de relación **"ManyToMany"**, se crea una tabla empleado_tarea, que se llenará con la ID del empleado y la tarea que se le asigne.

Para poder asignar una tarea a un empleado es imprescindible tener una tarea creada, si no saltará este mensaje:

<div>
  <img src="img/empleat/AsignaTareaEmpl_Vacio.png" alt="Error: crea una tarea para asignarla a un empleado" width="550"> 
</div>
<br>

Si existen registros de tareas, se muestran todas y esperará a que se seleccione la ID de la tarea que desea asignarle al empleado, por último se muestra un mensaje de confirmación.

<div>
  <img src="img/empleat/AsignaTareaEmpl_Bien.png" alt="Promt Empleat" width="650"> 
</div>
<br>

Prueba de creación, empleado con ID=1 y tarea con ID=1: 

<div>
  <img src="img/empleat/PruebaManytoMany.png" alt="Promt Empleat" width="450"> 
</div>
<br>


- **EXCEPTION DUPLICATE**

Estos tres primeros casos, manejaré la excepción de valores duplicados en las tablas. Comenzando con las columnas duplicadas al crear nuevos registros. Como ejemplo en la creación y en la modificación pondré al empleado con ID = 1:

<div>
  <img src="img/empleat/duplicate/Empleado_referencia.png" alt="Promt Empleat" width="700"> 
</div>
<br>


**CREACIÓN** <br>

Si intento crear un empleado con el mismo DNI que el empleado de referencia que es **"12345678A"**, se maneja el error lanzando un mensaje de error personalizado cuando se detecta una duplicación, en este caso el DNI.

<div>
  <img src="img/empleat/duplicate/CreaDni.png" alt="Promt Empleat" width="700"> 
</div>
<br>

Si intento crear un empleado con el mismo correo que el empleado de referencia que es **"juan.perez@email.com"**, se maneja el error lanzando un mensaje de error personalizado cuando se detecta una duplicación, en este caso el Correo.

<div>
  <img src="img/empleat/duplicate/CreaCorreo.png" alt="Promt Empleat" width="700"> 
</div>
<br>

Si intento crear un empleado con el mismo teléfono que el empleado de referencia que es **"654321987"**, se maneja el error lanzando un mensaje de error personalizado cuando se detecta una duplicación, en este caso el teléfono.

<div>
  <img src="img/empleat/duplicate/CreaTelefono.png" alt="Promt Empleat" width="700"> 
</div>
<br>

**MODIFICACIÓN** <br>

A la hora de modificar una columna, ocurre lo mismo, si la modificación que se hace interfiere con una columna que tiene una constraint unique, lanzará un mensaje de error personalizado cuando se detecta una duplicación, en este caso el DNI.


<div>
  <img src="img/empleat/duplicate/ModificaDni.png" alt="Promt Empleat" width="700"> 
</div>
<br>

Si intento cambiar el correo de un empleado al correo del empleado de referencia, lanzará un mensaje de error personalizado cuando se detecta una duplicación, en este caso el correo.

<div>
  <img src="img/empleat/duplicate/ModificaCorreo.png" alt="Promt Empleat" width="700"> 
</div>
<br>

Si intento cambiar el teléfono de un empleado al teléfono del empleado de referencia, lanzará un mensaje de error personalizado cuando se detecta una duplicación, en este caso el teléfono.

<div>
  <img src="img/empleat/duplicate/ModificaTelefono.png" alt="Promt Empleat" width="700"> 
</div>
<br>

<br>

# TASCA

Al selecionar la opción 3 en el main, entrará a la tabla Tasca.

<div>
  <img src="img/tasca/Prompt_Tasca.png" alt="Promt Empleat" width="300"> 
</div>
<br>

- **CREAR** 

Para crear una tarea se le debe poner un título y una descripción (no puden estar vacíos).

<div>
  <img src="img/tasca/Crea_tasca.png" alt="Promt Empleat" width="450"> 
</div>
<br>

- **ENCUENTRA**

Para encontrar una tarea, se llamarán a los métodos que muestran todas las tareas registradas y al que encuentra la tarea por su ID.

Al introducir un ID existente, se mostrará el toString() de la entidad.

<div>
  <img src="img/tasca/Encuentra_tasca.png" alt="Promt Empleat" width="650"> 
</div>
<br>


- **MODIFICA**

Para modificar una tarea se mostrarán todas las tareas y se escogerá la ID de la tarea que se desea modificar.

Si se selecciona la primera opción, se le pedirá al usuario un nuevo título (no puede estar vacío).
<div>
  <img src="img/tasca/modifica/titulo.png" alt="Promt Empleat" width="650"> 
</div>
<br>

Si se selecciona la segunda opción, se le pedirá al usuario una nueva descripción (no puede estar vacía).

<div>
  <img src="img/tasca/modifica/descripcion.png" alt="Promt Empleat" width="650"> 
</div>
<br>

Si se selecciona la tercera opción, se le pedirá al usuario nuevo título y descripción.

<div>
  <img src="img/tasca/modifica/todos.png" alt="Promt Empleat" width="650"> 
</div>
<br>

- **REMOVE**

Al escoger la opción de eliminar, se llama a 2 métodos, uno que muestra todos las tareas existentes y otro que escoge una tarea por su ID.

Al escoger una ID existente, se le pedirá al usuario la confirmación para eliminar la tarea. <br> 
Si la respuesta es **"y"**, se procederá a la eliminación y mostrará un mensaje de confirmación. 

<div>
  <img src="img/tasca/removeHecho.png" alt="Promt Empleat" width="650"> 
</div>
<br>

Si la respuesta es **"n"** como en este caso, se cancelará la eliminación de la tarea seleccionada.

<div>
    <img src="img/tasca/removeCancelada.png" alt="Promt Empleat" width="650"> 
</div>
<br>

- **ASIGNA EMPLEADO A TAREA**

Al tener una relación ManyToMany con la entidad Empleat, se deberá añadir una empleado a una tarea y viceversa en un mismo método, cuyo método que ya tienen creadas estas dos entidades.

Al tener este tipo de relación **"ManyToMany"**, se crea una tabla empleado_tarea, que se llenará con la ID de la tarea y empleado que se le asigne.

Para poder asignar una tarea a un empleado es imprescindible tener una tarea creada, si no saltará este mensaje: ***"Cree un empleado para poder asignarla a una Tarea."***
<br>

Si existen registros de empleados, se muestran todos y esperará a que se seleccione la ID del empleado que desea asignarle a la tarea, por último se muestra un mensaje de confirmación.

<div>
  <img src="img/tasca/AsignaEmplTasca.png" alt="Promt Empleat" width="700"> 
</div>
<br>

Prueba de creación, tarea con ID=1 y empleado con ID=3: 

<div>
  <img src="img/tasca/pruebaEmplTasca.png" alt="Promt Empleat" width="700"> 
</div>
<br>


# HISTORIC

Al selecionar la opción 4 en el main, entrará a la tabla Historic.

<div>
  <img src="img/historic/Prompt_historic.png" alt="Promt Empleat" width="300"> 
</div>
<br>

- **CREAR** 

Como existe una relacion entre Tasca e Historic de OneToMany, para hacer un registro de en Historic, se se le deba asignar una tarea.

Una vez obteniendo la tarea para el histórico, se le pedirá al usuario una fecha de inicio y final.

Ambos estan bajo las condiciones de un regex, cuya condición es que las fechas tengan 2 tipos de formato que son: **día/mes/año** o **día-mes-año**.

<div>
  <img src="img/historic/creaHistorica+Error.png" alt="Promt Empleat" width="650"> 
</div>
<br>

- **ENCUENTRA**

Para encontrar un histórico, se llamarán al método que muestra todos los históricos registrados y la obtención de un histórico por su ID.

Por último muestra el toString() de la entidad.

<div>
  <img src="img/historic/Encuentra_historico.png" alt="Promt Empleat" width="550"> 
</div>
<br>

- **MODIFICA**

Para modificar un histórico se mostrarán todos los históricos y se escogerá la ID del histórico que se desea modificar.<br>

Si se selecciona la primera opción, se le pedirá al usuario una nueva fecha de inicio (no puede estar vacía y debe cumplir el formato).
<div>
  <img src="img/historic/modifica/inicio.png" alt="Promt Empleat" width="450"> 
</div>
<br>

Si se selecciona la segunda opción, se le pedirá al usuario una nueva fecha final (no puede estar vacía y debe cumplir el formato).

<div>
  <img src="img/historic/modifica/final.png" alt="Promt Empleat" width="450"> 
</div>
<br>

Si se selecciona la tercera opción, se le pedirá al usuario una nueva tarea, mostrando así, todas las tareas disponibles para escoger una ID existente y asignarla al histórico.

<div>
  <img src="img/historic/modifica/tasca.png" alt="Promt Empleat" width="650"> 
</div>
<br>

Si se selecciona la cuarta opción, se le pedirá al usuario una nueva fecha de inicio y final y una nueva tarea.

<div>
  <img src="img/historic/modifica/todos.png" alt="Promt Empleat" width="650"> 
</div>
<br>

- **REMOVE**

Al escoger la opción de eliminar, se llama a 2 métodos, uno que muestra todos los históricos existentes y otro que escoge un histórico por su ID.

Al escoger una ID existente, se le pedirá al usuario la confirmación para eliminar el histórico. <br> 
Si la respuesta es **"y"**, se procederá a la eliminación y mostrará un mensaje de confirmación. 

<div>
  <img src="img/historic/removeHecho.png" alt="Promt Empleat" width="450"> 
</div>
<br>

Si la respuesta es **"n"** como en este caso, se cancelará la eliminación del histórico seleccionado.

<div>
  <img src="img/historic/removeCancelado.png" alt="Promt Empleat" width="450"> 
</div>
<br>