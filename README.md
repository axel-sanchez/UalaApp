# UalaApp
Este es el proyecto prueba de Avenga, con esta app se puede ver y buscar ciudades, acceder para ver su ubicación en un mapa y ver un listado de ciudades favoritas.

# Experiencia de usuario
Este proyecto contiene las siguientes características:

* La pantalla principal donde hay un listado de ciudades.
* La segunda pantalla donde se ve un listado de ciudades que fueron marcadas como favorito.
* Una vista de un mapa donde se ve marcada la ubicación de la ciudad (se accede haciendo click en un item de la primera pantalla).
  
# Capturas de pantalla

<p align="center">
  <img width="270" height="555" src="https://github.com/user-attachments/assets/29f696a9-3330-4a81-aeeb-42a14e623473">
  <img width="270" height="555" src="https://github.com/user-attachments/assets/9202af63-2774-4346-9e85-78a7bd699afd">
</p>

<p align="center">
  <img width="270" height="555" src="https://github.com/user-attachments/assets/b8defcbd-717a-4da3-8309-4937e45ce22d">
  <img width="270" height="555" src="https://github.com/user-attachments/assets/2b867942-f2d7-4917-806c-e8fd3be4daff">
</p>

<p align="center">
  <img width="555" height="270" src="https://github.com/user-attachments/assets/5996c011-2811-4300-9053-5c67f5e844cd">
</p>

## Guía de implementación
Traigo la información desde: 
https://gist.githubusercontent.com/hernan-uala/dce8843a8edbe0b0018b32e137bc2b3a/raw/0996accf70cb0ca0e16f9a99e0ee185fafca7af1/cities.json

### Arquitectura
Este proyecto implementa el patrón de arquitectura MVVM y sigue buenas prácticas de Clean Architecture para hacer un código más independiente, mantenible y sencillo.

#### Capas
* Presentation: Screens Composables, Viewmodels.
* Data: contiene la implementación del repositorio y los sources donde se conecta con la api y con la base de datos.
* Domain: contiene los casos de uso y la definición del repositorio.
Este proyecto usa ViewModel y Live Data para almacenar y manejar datos, así como comunicar cambios hacia la vista de forma reactiva.

### Administrador de solicitudes: Retrofit

Este proyecto utiliza Retrofit para mostrar las ciudades desde una API.

### Inyección de dependencia - Dagger

Este proyecto utiliza Dagger para gestionar la inyección de dependencia.

### Persistencia de datos - Room

Este proyecto utiliza la base de datos de Room para almacenar las ciudades.

### Testing

La app posee tests hechos con JUnit4

### Patrones de diseño

Utilizo algunos patrones de diseño como Observer, Singleton, Builder

# Guía de instalación
En caso de no tener instalado Android Studio, descargue la última versión estable. Una vez que tenemos el programa instalado vamos a Get from Version Control y vamos a pegar https://github.com/axel-sanchez/UalaApp.git Una vez hecho eso se va a clonar el proyecto, lo que resta sería conectar un celular y darle al botón verde de Run 'app'
