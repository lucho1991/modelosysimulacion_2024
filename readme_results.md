# Simulación: Caso Predador - Presa
## Algoritmos utilizados
#### En este caso nos basamos en el siguiente diagrama de simulación para realizar el algoritmo en cada paso de la simulación:
![Diagrama de Simulación]()
#### Considerando que fue calculado con base en un caso ideal de 500 liebres (presa) y 10 zorros (predador).
#### En esta simulación, hay ciertos parámetros que se dieron como supuestos a la hora de calcular la forma en que resultaban afectadas las distintas variables de la simulación, como lo es la tasa de mortalidad de los zorros si estos no tienen liebres para comer, que fue del 20% y la tasa de natalidad de las liebres que es del 8% siempre y cuando haya terreno para seguir ocupando y no hayan zorros que las cacen. Estos incrementos o reducciones en la población de zorros y liebres son considerados de una semana a la siguiente (una semana es el tiempo transcurrido entre iteraciones por defecto, a esto lo llamamos delta_t).


#### Este algoritmo funciona de la siguiente manera:
#### Primero consideramos los parámetros de entrada para la simulación, que son:
* #### Tasa de natalidad de liebres
* #### Tasa de mortalidad de zorros
* #### Capacidad inicial del terreno (máxima cantidad de liebres del terreno).
* #### Delta T (ΔT), que indica el tiempo transcurrido entre iteraciones, si este es de, por ej.: 1, representa una semana transcurrida.
#### Además consideramos las condiciones iniciales, que son la cantidad inicial de zorros y liebres.

#### Como nuestro objetivo es calcular la cantidad de liebres y zorros de una semana a la siguiente, tenemos que observar cuales son las variables que intervienen para dicho cálculo, estas se observan en el diagrama anterior.
Los cálculos realizados son los siguientes:
1. Calculamos la **capacidad actual del terreno**, restando la cantidad de liebres a la capacidad inicial.
2. Calculamos el **incremento de liebres** que habrá en la próxima semana, la cual está limitada por la capacidad actual del terreno, dado que si esta es cero, este incremento será cero. Es por ello que se implementó un cociente que representa la proporción de terreno disponible (este cociente funciona como un limitante del crecimiento de liebres), este fue multiplicado por la cantidad de liebres que nacen en la próxima iteración, que es lo que realmente estábamos buscando, esto último esta dado en función de la tasa de natalidad de liebres.
3. Calculamos la **disminución de los zorros**, en función de su tasa de natalidad multiplicado por la cantidad actual de zorros. Tener en cuenta que los zorros no tienen ninguna limitación en cuanto al terreno, por que no tienen que interactuar con él.
4. Calculamos la **variable caza**, que representa cantidad de encuentros posibles entre zorros y liebres, como el producto entre la cantidad de zorros y liebres.
5. Calculamos la **nueva cantidad de liebres**, la cual se verá aumentada por el incremento de las liebres en la semana, pero a su vez, reducida por la cantidad de liebres que se mueren por encuentro posible debido a la caza de los zorros. Debemos considerar que por cada encuentro entre un zorro y una liebre, una liebre muere, pero como hablamos de encuentros posibles, esto lo calculamos en función de la cantidad inicial de liebres en nuestra población ideal (10 zorros y 500 liebres), como hay 10 zorros, la cantidad de liebres que mueren de un ciclo al siguiente es de 10 liebres por los 5000 encuentros posibles (10/5000 = 0.002 liebres). De esta manera calculamos la cantidad de liebres que mueren por encuentro posible.
6. Por último, necesitamos calcular la **nueva cantidad de zorros**, la cual se verá reducida por la disminución de zorros, pero además se considerará los zorros que sobreviven de un ciclo al siguiente. Este último valor está dado por la cantidad de zorros que sobreviven en la totalidad de posibles encuentros (variable caza), siempre considerando que esta cantidad de zorros que sobreviven por encuentro se basa en la población ideal.

#### Esto se repetirá hasta que transcurra una determinada cantidad de semanas.

## Pruebas de simulación

### Prueba por defecto (2500 liebres y 2 zorros, tasa natalidad liebres: 0.8, tasa mortalidad zorros 0.2, capacidad terreno: 1400 liebres):

![Diagrama Poblacional]()
![Diagrama de Fase]()

En un inicio comenzamos con 2500 liebres, 2 zorros y una capacidad del terreno de 1400 liebres. A medida que pasa el tiempo la cantidad de liebres se va reduciendo, por qué estas están limitadas por el terreno y por qué, a su vez, están siendo cazadas por los zorros. A medida que sucede esto último, la cantidad de zorros se irá incrementando abruptamente, dado que tienen con que alimentarse. Sin embargo, al reducir la población de liebres debido a la caza, la población de zorros volverá a reducirse (por su taza de mortalidad). Luego de esto, como las liebres podrán reproducirse sin ningun problema (en esta circunstancia el terreno no estará lleno), su población aumentará y la de zorros se reducirá. A fin de cuentas, esto se repetirá de forma sucesiva hasta tender a lograr un punto de estabilidad o equilibrio para cada población, el cual podemos observar en el centro del diagrama de fase o al final del diagrama poblacional y es de aproximadamente 492 liebres y 25 zorros (considerar que esto es una tendencia dado que las poblaciones seguirían oscilando).

### Prueba N°2 (500 liebres, 10 zorros, tasa natalidad liebres: 0.5, tasa mortalidad zorros 0.1, capacidad terreno: 1400 liebres):

![Diagrama Poblacional]()
![Diagrama de Fase]()

Considerando los parámetros iniciales, vemos que este caso tiene la particularidad de que, al finalizar las 500 semanas, la cantidad de zorros supera a la cantidad de liebres, esto no ocurre en el caso anterior. Podemos observar que, en un comienzo, las liebres empiezan a crecer constantemente, dado que tienen terreno disponible, hasta un aproximado de 1200 liebres. Por otro lado, los zorros empiezan a tener más encuentros con las liebres, dado al aumento de la variable caza, incrementado su población por tener con que alimentarse y reduciendo la de las liebres en consecuencia. Por último, vemos que, gracias a que la tasa de natalidad de liebres es mucho mayor a la tasa de mortalidad de zorros, los periodos donde a estos últimos les falte presas para cazar serán muy reducidos, tendiendo a lograr un punto de estabilidad o equilibrio para ambas poblaciones, y este es de 250 liebres y 205 zorros.


### Prueba N°2 (500 liebres, 10 zorros, tasa natalidad liebres: 0.1, tasa mortalidad zorros 0.5, capacidad terreno: 1400 liebres):

![Diagrama Poblacional]()
![Diagrama de Fase]()

En esta última simulación, vemos que los cambios o perturbaciones que se producen en las poblaciones son pocos, dado que podemos observar que se tiende a lograr la estabilidad rápidamente (1250 liebres y 5 zorros). En un principio las liebres crecen hasta ser limitadas por el tamaño del terreno (1400 liebres), para luego ser cazadas por los zorros. Sin embargo, el aumento en la población de zorros realmente es pequeño, y esto se debe a su alta tasa de mortalidad.