<img src="images/icon.png" align="right" height="166" width="200"/>

# PaleoSW: fósiles y yacimientos paleontológicos en la web semántica 
 Trabajo final de la asignatura Web Semántica y Datos Enlazados.
 
 Realizado por: Jesús Pérez Martínez.
 
 Curso 2018-2019
 
## 1. Intoducción
El presente trabajo busca abordar y llevar a la práctica los conocimientos adquiridos en la asignatura "Web Semántica y Datos enlazados" del Máster en Investigación en Inteligencia Artificial. La Web semántica busca cambiar el modelo Web actual, añadiendo significado a la información mostrada, además de enlazar las relaciones entre los diferentes conceptos. Actualmente, existen multitud de sistemas y aplicaciones que usan los estándares y tecnologías definidas y desarrolladas al amparo del World Wide Web Consortium (W3C). Estas tecnologías destacan por su gran utilidad, transparencia y facilidad de reutilización en gran cantidad de proyectos y aplicaciones.

Por este motivo, tomando como punto de partida una porción del conjunto de datos total que podemos encontrar en <a href="https://paleobiodb.org/">The Paleobiology Database</a>, trataremos de traducir de una manera clara y versátil el conjunto de datos inicial (en formato *.csv*), a un grafo RDF. Este grafo, junto con el uso de una ontología, permitirá el enlazado de los conceptos de nuestra base de datos con los ya existentes en la Web. Por último, se creará una pequeña aplicación que permitirá la explotación del grafo para extraer información de utilidad.

## 2. Proceso de transformación
A lo largo de los siguientes puntos, se describirán cada uno de los puntos principales desde la eleción de la base de datos hasta su publicación. Así pues, comenzaremos explicando el contexto y las características del *dataset* y su licencia, para posteriormente analizarlo y adaptarlo a nuestra idea de grafo final. Proseguiremos definiendo la estrategia de nombrado de recursos y el desarrollo del vocabulario, donde se creará la ontología que usaremos en la transformación de la base de datos al grafo RDF.

Tras finalizar este bloque, se estudiará y definirá el enlazado de los recursos de nuestro grafo con los encontrados en la Web antes de realizar la publicación correspondiente.

### 2.1. Selección de la fuente de datos
La fuente de datos que usaremos como base es una porción de The Paleobiology Database (en adelante **PBDB**), correspondiente a la mitad este de la Península Ibérica y las Islas Baleares. PBDB es una base de datos pública de información paleontológica abierta, mantenida por un grupo internacional no gubernamental de paleontólogos. Permite explorar los datos desde el navegador y descargar los datos para analizarlos. Toda la información se encuentra bajo la licencia de Creative Commons **CC BY 4.0 International License**, que permite distribuir y modificar el contenido siempre que se atribuya la autoría del mismo.

<p align="center">
<img src="images/pbdb_black.png" height="350" width="430"/>
</p>

Personalmente, me ha parecido muy interesante usar esta base de datos en este trabajo, dado que no existe información clara y precisa en la Web semántica actual sobre los datos que en este *dataset* se abordan. El conjunto de datos descargado contiene **1842 registros** y ha sido obtenido del original (con 1371645 registros totales) aplicando los siguientes filtros que nos permite la <a href="https://paleobiodb.org/navigator/">aplicación web</a>:
* **Map extent**: ([38, -7], [42, 4])
* **Interval**: Mesozoic
* **Taxon**: Chordata

Tal y como se indica, sólo hemos elegido aquellos registros fósiles de vertebrados (*Chordata*) que pertenecen a la época de los dinosaurios (Mesozoico).

### 2.2. Análisis de datos
 
| Nombre de la columna | Motivo |
|---------|---------|
|flags | Información interna de la aplicación |
| record_type | Información interna de la aplicación |
| reid_no | Información interna de la aplicación |
| difference | 100% valores nulos |
| accepted_attr | Información interna de la aplicación |
| accepted_rank | Duplicado con identified_rank |
| late_interval | Eliminado por el gran número de valores nulos (1366/1842 = **75%**) |
| latlng_basis | Información interna de la aplicación |
| geogscale | Información interna de la aplicación |
| geogcomments | Información interna de la aplicación |
| protected | 100% valores nulos |
| cx_int_no | Información interna de la aplicación |
| stratgroup | Eliminado por el gran número de valores nulos (1225/1842 = **67%**) |
| member | Información interna de la aplicación |
| zone | Eliminado por el gran número de valores nulos (1804/1842 = **98%**) |
| localbed | Eliminado por pertenecer a detalles demasiado específicos del yacimiento + cantidad de valores nulos (1501/1842 = **82%** ) |
| localorder | Eliminado por pertenecer a detalles demasiado específicos del yacimiento + cantidad de valores nulos (1503/1842 = **82%** ) |
| regionalbed | 100% valores nulos |
| regionalorder | 100% valores nulos |
| regionalsection | Eliminado por el gran número de valores nulos (1841/1842 = **99.95%**) |
| stratcomments | Anotaciones del estrato |
| lithdescript | Anotaciones adicionales del estrato |
| lithadj1 | Anotaciones adicionales sobre la litología |
| fossilsfrom1 | Información interna de la aplicación |
| lithification1 | Eliminado por el gran número de valores nulos (1666/1842 = **91%**) |
| minor_lithology1 | Eliminado por el gran número de valores nulos (1494/1842 = **81%**) |
| lithology2 | Eliminado por el gran número de valores nulos (1255/1842 = **68%**) |
| lithadj2 | Anotaciones adicionales sobre la litología + eliminado por el gran número de valores nulos (1667/1842 = **91%**) |
| lithification1 | Eliminado por el gran número de valores nulos (1841/1842 = **99.95%**) |
| minor_lithology2 | Eliminado por el gran número de valores nulos (1765/1842 = **96%**) |
| fossilsfrom2 | Información interna de la aplicación |
| tectonic_setting | Eliminado por el gran número de valores nulos (1751/1842 = **95%**) |
| geology_comments | Anotaciones sobre la geología del yacimiento |
| occurrence_comments | Anotaciones sobre el hayazgo |
| created | Información interna de la aplicación |
| modified | Información interna de la aplicación |
| paleomodel | Información interna de la aplicación |
| geoplate | Información interna de la aplicación |
| paleolng | Información interna de la aplicación |
| paleolat | Información interna de la aplicación |


### 2.3. Estrategia de nombrado
### 2.4. Desarrollo del vocabulario
### 2.5. Proceso de transformación
### 2.6. Enlazado
### 2.7. Publicación
## 3. Aplicación y explotación
## 4. Conclusiones
## 5. Bibliografía
