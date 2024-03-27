##Trabajo Practico N° 1: Simulación: Depredador - Presa

### Clonar Repositorio

#### 1) Navega al directorio donde se clonará el repositorio.

#### 2) Ejecuta el clon git del repositorio:

```sh
git clone https://github.com/lucho1991/modelsandsimulation_2024_assignment1.git
```

### Pasos de Instalación

#### 1) Instala Java 11

Para instalar Java y ejecutar un programa, puedes seguir estos pasos:

- Instala el Java Development Kit (JDK):

Visita el sitio web oficial de Oracle o adopta OpenJDK para descargar e instalar el JDK apropiado para tu sistema operativo.

Sigue las instrucciones de instalación proporcionadas para tu sistema operativo específico.


### Compila el programa:

#### 1) Utilizar maven para compilar el proyecto

Navegar hasta el root del proyecto y escribir el comando:

````sh
mvn install
````

#### Ejecuta el programa:

Después de la compilación exitosa, puedes ejecutar el programa. Asegúrate de proporcionar los parámetros requeridos en el orden correcto. Los parámetros requeridos son los siguientes:

````yaml
2500 2 0.08 0.2 1400 1 500
````

Donde los argumentos son:

````yaml
cantLiebres: cantidad inicial de liebres.
cantZorros: cantidad inicial de zorros.
tasaNatalidadLiebres: tasa de natalidad de liebres.
tasaMortalidadZorros: tasa de mortalidad de zorros.
capacidadTerreno: capacidad del terreno.
deltaT: cantidad de semanas que transcurren entre cada iteración.
totalSemanas: cantidad total de semanas de la simulación.
````
##### Ejemplo de ejecución del programa:

```sh
java -jar .\target\LotkaVolterra-1.0.0.jar 2500 2 0.08 0.2 1400 1 500
```