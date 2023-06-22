# Introducción

Este trabajo es la tercera y última de una serie de entregas que forman
parte de la materia Interacción Hombre-Computadora (HCI) que consisten
en diseñar e implementar parcialmente un sitio Web y una aplicación
móvil (Android) que permitan administrar y controlar en forma remota
dispositivos inteligentes que se encuentren presentes en un hogar. Esta
entrega se enfoca en la implementación de la aplicación móvil.

# Archivos necesarios para construir el instalador de la aplicación

Se adjunta un archivo *.zip* con el código fuente de la app. Para que
esta pueda comunicarse con la API, se debe modificar el archivo
*build.gradle (:app)*, modificando la variable *API_BASE_URL* que se
encuentra en *buildTypes $\to$ release* por la IP de la computadora en
la que se esté corriendo.

Una vez configurada la IP de la computadora, se debe construir la *APK*.
Para esto, en el menú superior se debe ir a: *Build $\to$ Build
Bundle(s) / APK(s) $\to$ Build APK(s)*. Esto producirá un archivo
*.apk*, que luego deberá ser copiado al dispositivo en el que se quiera
realizar la instalación.

# Instructivo de instalación

Una vez que el archivo *.apk* se encuentre en el dispositivo, se debe
utilizar un explorador de archivos y abrir el archivo mencionado.
Posteriormente, seguir los pasos de instalación indicados.
